package com.bajidan.cafe_ms.serviceImp;

import com.bajidan.cafe_ms.JWT.CustomerUserDetailsService;
import com.bajidan.cafe_ms.JWT.JwtFilter;
import com.bajidan.cafe_ms.JWT.JwtUtil;
import com.bajidan.cafe_ms.constants.CafeConstants;
import com.bajidan.cafe_ms.model.User;
import com.bajidan.cafe_ms.repository.UserRepository;
import com.bajidan.cafe_ms.serviceInterface.UserService;
import com.bajidan.cafe_ms.util.CafeUtil;
import com.bajidan.cafe_ms.util.EmailUtil;
import com.bajidan.cafe_ms.wrapper.UserWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomerUserDetailsService customerUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    EmailUtil emailUtil;


    @Override
    public ResponseEntity<String> saveSignUp(Map<String, String> body) {
        log.info("Inside signup {}", body);
        try {
            if(isValid(body)) {
                User user = userRepository.findByEmail(body.get("email"));
               // userRepository.findByEmail(body.get("email")).orElse(userRepository.save(user(body)));
                if(Objects.isNull(user)) {
                    userRepository.save(user(body));
                    return CafeUtil.getResponse("Successfully registered", HttpStatus.OK);
                }
                else {
                    return CafeUtil.getResponse("Email already exist", HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafeUtil.getResponse(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> body) {
        log.info("Inside login");

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(body.get("email"), body.get("password"))
            );
            if(auth.isAuthenticated()) {
                if(customerUserDetailsService.getUser().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUserDetailsService.getUser().getEmail(),
                                    customerUserDetailsService.getUser().getRole()) + "\"}"
                            , HttpStatus.OK);
                } else {
                    return CafeUtil.getResponse("Wait for admin approval", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            log.error("{ }", e);
        }
        return CafeUtil.getResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
       try {
           if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(getUsers(), HttpStatus.OK);
           } else {
               return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
           }

        } catch (Exception e) {
           e.printStackTrace();
        }
      return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> user) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> currentUser = userRepository.findById(Integer.parseInt(user.get("id")));

                if(currentUser.isPresent()){
                    userRepository.updateUserStatus(user.get("status"), Integer.parseInt(user.get("id")));
                    sendMailToAllAdmin(user.get("status"), currentUser.get().getEmail(), getAllAdminMail());
                    return CafeUtil.getResponse("Status Successfully updated", HttpStatus.CREATED);
                } else {
                    return CafeUtil.getResponse("User not found", HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafeUtil.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return new ResponseEntity<>("true", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestBody) {

        try {

            User user = userRepository.findByEmail(jwtFilter.getCurrentUsername());

            if (!user.equals(null)) {
                if(user.getPassword().equals(requestBody.get("oldPassword"))) {
                    user.setPassword(requestBody.get("newPassword"));
                    userRepository.save(user);
                    return CafeUtil.getResponse("Password updated successfully", HttpStatus.OK);
                }

                return CafeUtil.getResponse("Wrong Password", HttpStatus.BAD_REQUEST);
            }

            return CafeUtil.getResponse();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse();
    }

    @Override
    public ResponseEntity<String> forgetPassword(Map<String, String> requestBody) {
        try {
            User user = userRepository.findByEmail(requestBody.get("email"));

            if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
                emailUtil.forgetPassword(user.getEmail(), "Password recovery", user.getPassword());

                return CafeUtil.getResponse("Check your email for credentials", HttpStatus.OK);
            }

            return CafeUtil.getResponse("User not found", HttpStatus.NOT_FOUND);

//            if(user != null) {
//                emailUtil.sendSimpleMessage(user.getEmail(), "Password Recovery",
//                        "Password: " + user.getPassword());
//                return CafeUtil.getResponse("Email sent successfully", HttpStatus.OK);
//            }
// return CafeUtil.getResponse("email does not exist", HttpStatus.BAD_REQUEST);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse();
    }


    private boolean isValid(Map<String, String> body) {
        return body.containsKey("name")
                && body.containsKey("contactNumber")
                && body.containsKey("email") && body.containsKey("password");
    }

    private User user(Map<String, String> body) {
        User newUser = new User();
        newUser.setName(body.get("name"));
        newUser.setContactNumber(body.get("contactNumber"));
        newUser.setEmail(body.get("email"));
        newUser.setPassword(body.get("password"));
        newUser.setRole("user");
        newUser.setStatus("false");
        return newUser;
    }

    private List<UserWrapper> getUsers() {
        UserWrapper userWrapper = new UserWrapper();
        List<User> userList = userRepository.findAll();
        Function<User, UserWrapper> fullUser =  newUser ->
                new UserWrapper(newUser.getId(), newUser.getName(),
                        newUser.getContactNumber(), newUser.getEmail(),
                        newUser.getStatus());

        return userList.stream().map(fullUser).collect(Collectors.toList());
    }
    
    private List<String> getAllAdminMail() {
        List<User> userList = userRepository.findAll();
        return new ArrayList<>(
                userList.stream()
                        .filter(x -> x.getRole()
                                .equalsIgnoreCase("admin"))
                        .map(User::getEmail).toList()
        ) ;
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdminEmail) {
        allAdminEmail.remove(jwtFilter.getCurrentUsername());

        if(status != null && status.equalsIgnoreCase("true")) {
            emailUtil.sendSimpleMessage(jwtFilter.getCurrentUsername(),
                    "Account Approve", "USER:- " + user + "\n is approved by \nADMIN:- " + jwtFilter.getCurrentUsername(),
                    allAdminEmail);
        } else {
            emailUtil.sendSimpleMessage(jwtFilter.getCurrentUsername(),
                    "Account Disable", "USER:- " + user + "\n is disable by \nADMIN" + jwtFilter.getCurrentUsername(),
                    allAdminEmail);
        }
    }



}



package com.bajidan.cafe_ms.serviceImp;

import com.bajidan.cafe_ms.JWT.CustomerUserDetailsService;
import com.bajidan.cafe_ms.JWT.JwtUtil;
import com.bajidan.cafe_ms.constants.CafeConstants;
import com.bajidan.cafe_ms.model.User;
import com.bajidan.cafe_ms.repository.UserRepository;
import com.bajidan.cafe_ms.service.UserService;
import com.bajidan.cafe_ms.util.CafeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        return CafeUtil.getResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
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
}



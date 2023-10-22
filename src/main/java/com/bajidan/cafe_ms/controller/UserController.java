package com.bajidan.cafe_ms.controller;

import com.bajidan.cafe_ms.constants.CafeConstants;
import com.bajidan.cafe_ms.model.User;
import com.bajidan.cafe_ms.rest.UserRest;
import com.bajidan.cafe_ms.serviceImp.UserServiceImp;
import com.bajidan.cafe_ms.util.CafeUtil;
import com.bajidan.cafe_ms.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController implements UserRest {

    @Autowired
    private UserServiceImp userServiceImp;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestBody)  {
        try {
            return userServiceImp.saveSignUp(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userServiceImp.login(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            return userServiceImp.getAllUsers();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> user) {
        try{

            if(userServiceImp.updateStatus(user).equals(CafeUtil.getResponse(CafeUtil.getText(), HttpStatus.BAD_REQUEST))) {
                return CafeUtil.getResponse(CafeUtil.getText(), HttpStatus.BAD_REQUEST);
            }

            return CafeUtil.getResponse("Successfully updated", HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}


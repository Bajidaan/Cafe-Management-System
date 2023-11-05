package com.bajidan.cafe_ms.controller;

import com.bajidan.cafe_ms.constants.CafeConstants;
import com.bajidan.cafe_ms.restInterface.UserRest;
import com.bajidan.cafe_ms.serviceImp.UserServiceImp;
import com.bajidan.cafe_ms.util.CafeUtil;
import com.bajidan.cafe_ms.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            return userServiceImp.updateStatus(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse();

    }

    @Override
    public ResponseEntity<String> checkToken() {
          return userServiceImp.checkToken();

    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestBody) {
        try {
            return userServiceImp.changePassword(requestBody);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse();
    }

    @Override
    public ResponseEntity<String> forgetPassword(Map<String, String> requestBody) {
        try {
            return userServiceImp.forgetPassword(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse();
    }

}


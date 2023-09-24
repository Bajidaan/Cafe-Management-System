package com.bajidan.cafe_ms.controller;

import com.bajidan.cafe_ms.constants.CafeConstants;
import com.bajidan.cafe_ms.rest.UserRest;
import com.bajidan.cafe_ms.serviceImp.UserServiceImp;
import com.bajidan.cafe_ms.util.CafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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




}

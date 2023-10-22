package com.bajidan.cafe_ms.service;

import com.bajidan.cafe_ms.model.User;
import com.bajidan.cafe_ms.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface UserService {
    public ResponseEntity<String> saveSignUp(Map<String, String> body);
    public ResponseEntity<String> login(Map<String, String> body);

    public ResponseEntity<List<UserWrapper>> getAllUsers();
    public ResponseEntity<String> updateStatus(Map<String, String> user);
}

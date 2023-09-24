package com.bajidan.cafe_ms.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface UserService {
    public ResponseEntity<String> saveSignUp(Map<String, String> body);
}

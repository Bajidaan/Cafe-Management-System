package com.bajidan.cafe_ms.serviceInterface;

import com.bajidan.cafe_ms.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface UserService {
    public ResponseEntity<String> saveSignUp(Map<String, String> body);
    public ResponseEntity<String> login(Map<String, String> body);
    ResponseEntity<List<UserWrapper>> getAllUsers();
    ResponseEntity<String> updateStatus(Map<String, String> user);

    ResponseEntity<String> checkToken();
    ResponseEntity<String> changePassword(Map<String, String> requestBody);

    ResponseEntity<String> forgetPassword(Map<String, String> requestBody);
}

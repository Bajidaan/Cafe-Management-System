package com.bajidan.cafe_ms.restInterface;

import com.bajidan.cafe_ms.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.List;
import java.util.Map;

@RequestMapping("user")
public interface UserRest {

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody Map<String, String> requestBody);

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap);

    @GetMapping("/get")
    ResponseEntity<List<UserWrapper>> getAllUser();
    @PostMapping("/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String, String> user);

    @GetMapping("/checkToken")
   ResponseEntity<String> checkToken();

    @PostMapping("/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestBody);

    @PostMapping("/forgetPassword")
    ResponseEntity<String> forgetPassword(@RequestBody Map<String, String> requestBody);



}

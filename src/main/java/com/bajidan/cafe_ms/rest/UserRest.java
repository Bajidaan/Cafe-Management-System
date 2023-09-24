package com.bajidan.cafe_ms.rest;

import com.bajidan.cafe_ms.constants.CafeConstants;
import com.bajidan.cafe_ms.util.CafeUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.Map;

@RequestMapping("user")
public interface UserRest {
//    {
//        "name": "jason",
//            "contactNumber": "jason12",
//            "email": "",
//            "password": ""
//    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody Map<String, String> requestBody);
}

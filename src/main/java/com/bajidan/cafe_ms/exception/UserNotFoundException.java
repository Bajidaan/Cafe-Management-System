package com.bajidan.cafe_ms.exception;

import com.bajidan.cafe_ms.util.CafeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException() {}

    public static ResponseEntity<String> userNotFound() {
        return CafeUtil.getResponse("User not found", HttpStatus.BAD_REQUEST);
    }
}

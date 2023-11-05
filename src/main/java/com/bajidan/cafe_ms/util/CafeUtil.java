package com.bajidan.cafe_ms.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtil {
    private CafeUtil() {

    }

    public static ResponseEntity<String> getResponse(String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\"" + message + "}", httpStatus);
    }

    public static ResponseEntity<String> getResponse() {
        return new ResponseEntity<String>("{\"message\":\" Something went wrong }", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}


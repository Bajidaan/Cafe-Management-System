package com.bajidan.cafe_ms.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CafeUtil {

    private static String text;
    private CafeUtil() {

    }

    public static ResponseEntity<String> getResponse(String message, HttpStatus httpStatus) {
        text = message;
        return new ResponseEntity<String>("{\"message\":\"" + message + "}", httpStatus);
    }

    public static String getText() {
        return text;
    }
}


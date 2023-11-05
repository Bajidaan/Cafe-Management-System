package com.bajidan.cafe_ms.controller;

import com.bajidan.cafe_ms.model.Category;
import com.bajidan.cafe_ms.restInterface.CategoryRest;
import com.bajidan.cafe_ms.serviceImp.CategoryServiceImp;
import com.bajidan.cafe_ms.util.CafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class CategoryController implements CategoryRest {
    @Autowired
    CategoryServiceImp categoryServiceImp;

    @Override
    public ResponseEntity<String> addCategory(Map<String, String> requestBody) {
        try {
           return  categoryServiceImp.addCategory(requestBody);

        } catch (Exception e) {
            e.printStackTrace();
        }
       return CafeUtil.getResponse();
    }

    @Override
    public  ResponseEntity<List<Category>> getAllCategory() {
        try {
            return categoryServiceImp.getAllCategory();

        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestBody) {
        try {
            return categoryServiceImp.updateCategory(requestBody);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse();
    }
}

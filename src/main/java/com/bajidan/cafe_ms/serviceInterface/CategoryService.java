package com.bajidan.cafe_ms.serviceInterface;

import com.bajidan.cafe_ms.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {

    ResponseEntity<String> addCategory(Map<String, String> requestBody);

    ResponseEntity<List<Category>> getAllCategory();

    ResponseEntity<String> updateCategory(Map<String, String> requestBody);
}

package com.bajidan.cafe_ms.restInterface;

import com.bajidan.cafe_ms.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/category")
public interface CategoryRest {

    @PostMapping("/add")
    ResponseEntity<String> addCategory(@RequestBody Map<String, String> requestBody);

    @GetMapping("/get")
    ResponseEntity<List<Category>> getAllCategory();

    @PostMapping("/update")
    ResponseEntity<String> updateCategory(@RequestBody Map<String, String> requestBody);

}

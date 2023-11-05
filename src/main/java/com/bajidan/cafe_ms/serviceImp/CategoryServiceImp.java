package com.bajidan.cafe_ms.serviceImp;

import com.bajidan.cafe_ms.JWT.JwtFilter;
import com.bajidan.cafe_ms.constants.CafeConstants;
import com.bajidan.cafe_ms.model.Category;
import com.bajidan.cafe_ms.repository.CategoryRepository;
import com.bajidan.cafe_ms.serviceInterface.CategoryService;
import com.bajidan.cafe_ms.util.CafeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    CategoryRepository categoryRepository;

    public ResponseEntity<String> addCategory(Map<String, String> requestBody) {
        try {
            if(jwtFilter.isAdmin()) {
                if(validateCategoryMap(requestBody, false)) {
                    Category category = getCategoryFromMap(requestBody, false);
                    categoryRepository.save(category);

                    return CafeUtil.getResponse("Category added successfully", HttpStatus.OK);
                }

                return CafeUtil.getResponse("Wrong input", HttpStatus.BAD_REQUEST);
            }
            return CafeUtil.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse();
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory() {
        try {
            if(jwtFilter.isAdmin()) {

                return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestBody) {
        try {
            if(jwtFilter.isAdmin()) {
                if(validateCategoryMap(requestBody, true)) {
                    Optional<Category> categoryById = categoryRepository.findById(Integer.parseInt(requestBody.get("id")));

                    if(categoryById.isPresent()) {
                        Category category = getCategoryFromMap(requestBody, true);

                        categoryRepository.save(category);

                        return CafeUtil.getResponse("Category Successfully Updated", HttpStatus.OK);
                    }

                    return CafeUtil.getResponse("Category id doesnt exist", HttpStatus.OK);
                }
            }
            return CafeUtil.getResponse(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);


        } catch(Exception e) {
            e.printStackTrace();
        }
        return CafeUtil.getResponse();
    }

    private boolean validateCategoryMap(Map<String, String> requestBody, boolean validateId) {
        if(requestBody.containsKey("name")) {
            if(requestBody.containsKey("id") && validateId) {
                return true;
            } else if(!validateId) {
                return true;
            }
        }

        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestBody, Boolean isAdd) {
        Category category = new Category();

        if(isAdd) {
            category.setId(Integer.parseInt(requestBody.get("id")));
        }

        category.setName(requestBody.get("name"));

        return category;
    }
}

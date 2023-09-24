package com.bajidan.cafe_ms.repository;

import com.bajidan.cafe_ms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String name);

}

package com.bajidan.cafe_ms.repository;

import com.bajidan.cafe_ms.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String name);

    @Query(
            "UPDATE User u set u.status=:status where u.id=:id"
    )
    @Transactional
    @Modifying
    void updateUserStatus(@Param("status") String status, @Param("id") Integer id);


}

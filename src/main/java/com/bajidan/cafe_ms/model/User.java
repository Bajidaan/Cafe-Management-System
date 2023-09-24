package com.bajidan.cafe_ms.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
@Table(name = "users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "contactNumber")
    private String contactNumber;
    private String email;
    private String password;
    private String status;
    private String role;

    public User(String name, String contactName, String email, String password) {
        this.name = name;
        this.contactNumber = contactName;
        this.email = email;
        this.password = password;
    }
    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

package com.bajidan.cafe_ms.wrapper;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWrapper {

    private Integer id;
    private String name;
    private String contactNumber;
    private String email;
    private String status;

}

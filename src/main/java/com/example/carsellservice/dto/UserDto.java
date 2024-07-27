package com.example.carsellservice.dto;

import com.example.carsellservice.enums.UserRole;
import lombok.Data;

@Data
public class UserDto { //this is used by the application to input data used in the structure below
    private Long id;
    private String name;
    private String email;
    private String password;
    private UserRole userRole;
}

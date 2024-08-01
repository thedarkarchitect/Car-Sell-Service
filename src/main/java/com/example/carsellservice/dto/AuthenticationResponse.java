package com.example.carsellservice.dto;

import com.example.carsellservice.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse { //this is returned by the backend in all wrapped in jwt token

    private String jwt;

    private Long userId;

    private UserRole userRole;
}

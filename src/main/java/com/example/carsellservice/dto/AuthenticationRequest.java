package com.example.carsellservice.dto;

import lombok.Data;

@Data
public class AuthenticationRequest { //this is used for login
    private String email;
    private String password;
}

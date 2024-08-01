package com.example.carsellservice.dto;

import lombok.Data;

@Data
public class SignupRequest { //used to sign up

    private String email;

    private String password;

    private String name;
}

package com.example.carsellservice.service.auth;

import com.example.carsellservice.dto.SignupRequest;
import com.example.carsellservice.dto.UserDto;

public interface AuthService {
    Boolean hasUserWithEmail(String email);

    UserDto signup(SignupRequest signupRequest);
}

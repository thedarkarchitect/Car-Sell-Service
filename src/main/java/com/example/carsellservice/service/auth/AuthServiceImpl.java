package com.example.carsellservice.service.auth;

import com.example.carsellservice.dto.SignupRequest;
import com.example.carsellservice.dto.UserDto;
import com.example.carsellservice.entity.User;
import com.example.carsellservice.enums.UserRole;
import com.example.carsellservice.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct //this is used on a method that needs to be executed after DI is done to perform any initialization
    public void createAdminAccount() {//basically method runs at runtime after the DI happens
        Optional<User> optionalAdmin = userRepository.findByUserRole(UserRole.ADMIN);
        if(optionalAdmin.isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@test.com");
            admin.setUserRole(UserRole.ADMIN);
            admin.setPassword(new BCryptPasswordEncoder().encode(System.getenv("AdminPassword")));
            userRepository.save(admin);
            System.out.println("Admin account created successfully");
        } else {
            System.out.println("Admin account already exist!");
        }
    }


    @Override
    public Boolean hasUserWithEmail(String email) { //this checks if the email exits in datasource and returns a boolean on that status
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public UserDto signup(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        return userRepository.save(user).getUserDto();
    }
}

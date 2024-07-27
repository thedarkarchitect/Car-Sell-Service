package com.example.carsellservice.controller;

import com.example.carsellservice.dto.AuthenticationRequest;
import com.example.carsellservice.dto.AuthenticationResponse;
import com.example.carsellservice.dto.SignupRequest;
import com.example.carsellservice.dto.UserDto;
import com.example.carsellservice.entity.User;
import com.example.carsellservice.repositories.UserRepository;
import com.example.carsellservice.service.auth.AuthService;
import com.example.carsellservice.service.jwt.UserService;
import com.example.carsellservice.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    private final UserService userService;

    private final JWTUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User Already exist");
        UserDto userDto = authService.signup(signupRequest);
        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(//this will authenticate the user by comparing with data in the datasource
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch(BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or Password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String jwt = JWTUtil.generateToken(userDetails);
        AuthenticationResponse response = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            response.setJwt(jwt);
            response.setUserRole(optionalUser.get().getUserRole());
            response.setUserId(optionalUser.get().getId());
        }

        return response;//this is the Authentication Response;
    }
}

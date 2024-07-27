package com.example.carsellservice.service.jwt;

import com.example.carsellservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //this helps do dependency injection
public class UserServiceImpl implements UserService{ //this is all happening at the service layer to communicate with the persistence layer

    private final UserRepository userRepository; //this is being injected into the class to access method from the presistence layer that help


    @Override
    public UserDetailsService userDetailsService() { //userDetailService is used to retrieve user data from a backend datasource
        return new UserDetailsService() {
            //the method below takes a username as a parameter and returns a fully populated UserDetails object
            //the userDetail Object represents the authenticated User in the Spring Security and contains details like username, password, roles(authorites)  annd additional attributes
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return userRepository.findFirstByEmail(email)//this will help return a user by finding them in the datasource by their email
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }
}

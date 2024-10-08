package com.example.carsellservice.entity;

import com.example.carsellservice.dto.UserDto;
import com.example.carsellservice.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "\"user\"")
public class User implements UserDetails { //this holds the structure of the persisted data from the data source and inherits methods from the userDetails to secure the data from the datasource
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//this will auto generate numbers
    private Long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;

    public UserDto getUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setUserRole(userRole);
        return userDto;
    }

    //methods below are from the UserDetails class for security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //this returns user roles
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

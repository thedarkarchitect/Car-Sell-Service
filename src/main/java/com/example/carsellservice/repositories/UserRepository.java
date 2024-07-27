package com.example.carsellservice.repositories;

import com.example.carsellservice.entity.User;
import com.example.carsellservice.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { //this repository contains methods that help communicate with the datasource instead of using direct sql
    Optional<User> findFirstByEmail(String email); //Optional class represents either presence or absence of something in this case User

    Optional<User> findByUserRole(UserRole userRole);
}



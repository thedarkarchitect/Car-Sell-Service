package com.example.carsellservice.repositories;

import com.example.carsellservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByUserId(Long userId);
}

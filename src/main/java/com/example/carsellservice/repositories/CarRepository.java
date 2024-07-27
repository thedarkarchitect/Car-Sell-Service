package com.example.carsellservice.repositories;

import com.example.carsellservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}

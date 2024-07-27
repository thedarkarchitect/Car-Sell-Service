package com.example.carsellservice.service.admin;

import com.example.carsellservice.dto.CarDto;

import java.util.List;

public interface AdminService {
    List<CarDto> getAllCars();

    CarDto getCarById(Long id);

    void deleteCar(Long id);
}

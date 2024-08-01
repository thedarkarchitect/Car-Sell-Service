package com.example.carsellservice.service.admin;

import com.example.carsellservice.dto.CarDto;
import com.example.carsellservice.dto.SearchCarDto;

import java.util.List;

public interface AdminService {
    List<CarDto> getAllCars();

    CarDto getCarById(Long id);

    List<CarDto> searchCar(SearchCarDto searchCarDto);

    void deleteCar(Long id);
}

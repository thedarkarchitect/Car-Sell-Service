package com.example.carsellservice.service.customer;

import com.example.carsellservice.dto.CarDto;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    boolean createCar(CarDto carDto) throws IOException;

    List<CarDto> getAllCars();

    CarDto getCarById(Long id);

    void deleteCarById(Long id);
}

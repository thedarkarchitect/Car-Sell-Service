package com.example.carsellservice.service.customer;

import com.example.carsellservice.dto.CarDto;
import com.example.carsellservice.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    boolean createCar(CarDto carDto) throws IOException; //returns a boolean if the car is created for a customer

    List<CarDto> getAllCars(); // returns a list of cars

    CarDto getCarById(Long id);//returns a car by id

    boolean updateCar(Long id, CarDto carDto) throws IOException;

    void deleteCarById(Long id);//deletes a car from a customer list by id

    List<CarDto> searchCar(SearchCarDto searchCarDto);

    List<CarDto> getCustomerCars(Long userId);
}

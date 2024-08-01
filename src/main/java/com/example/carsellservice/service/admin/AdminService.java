package com.example.carsellservice.service.admin;

import com.example.carsellservice.dto.BidDto;
import com.example.carsellservice.dto.CarDto;
import com.example.carsellservice.dto.SearchCarDto;

import java.util.List;

public interface AdminService {
    List<CarDto> getAllCars();

    CarDto getCarById(Long id);

    List<CarDto> searchCar(SearchCarDto searchCarDto);

    List<BidDto> getBids();

    boolean changeBidStatus(Long bidId, String status);

    void deleteCar(Long id);
}

package com.example.carsellservice.controller;

import com.example.carsellservice.dto.CarDto;
import com.example.carsellservice.dto.SearchCarDto;
import com.example.carsellservice.service.admin.AdminService;
import com.example.carsellservice.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCar() {
        return ResponseEntity.ok(adminService.getAllCars());
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getCarById(id));
    }

    @PostMapping("/car/search")
    public ResponseEntity<List<CarDto>> searchCar(@RequestBody SearchCarDto searchCarDto) {
        return ResponseEntity.ok(adminService.searchCar(searchCarDto));
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        adminService.deleteCar(id);//using the service will delete the car from the dataSource
        return ResponseEntity.ok(null);
    }
}

package com.example.carsellservice.controller;

import com.example.carsellservice.dto.BidDto;
import com.example.carsellservice.dto.CarDto;
import com.example.carsellservice.dto.SearchCarDto;
import com.example.carsellservice.service.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/car")
    public ResponseEntity<?> addCar(@ModelAttribute CarDto carDto) throws IOException {//The @Modelattribute annotation binds HTTP request parameters to Java objects
        boolean success = customerService.createCar(carDto);
        if (success) return  ResponseEntity.status(HttpStatus.CREATED).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCar() {
        return ResponseEntity.ok(customerService.getAllCars());
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCarById(id));
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @ModelAttribute CarDto carDto) throws IOException {
        boolean success = customerService.updateCar(id, carDto);
        if(success) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/car/search")
    public ResponseEntity<List<CarDto>> searchCar(@RequestBody SearchCarDto searchCarDto) {
        return ResponseEntity.ok(customerService.searchCar(searchCarDto));
    }

    @GetMapping("/my-car/{userId}")
    public ResponseEntity<List<CarDto>> getCustomerCars(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getCustomerCars(userId));
    }

    @PostMapping("/car/bid")
    public ResponseEntity<?> bidOnACar(@RequestBody BidDto bidDto) {
        boolean success = customerService.bidOnCar(bidDto);
        if (success) return ResponseEntity.status(HttpStatus.OK).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/car/bid/{userId}")
    public ResponseEntity<List<BidDto>> getCustomerBids(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getBidsByUserId(userId));
    }

    @GetMapping("/car/{carId}/bids")
    public ResponseEntity<List<BidDto>> getBidsByCar(@PathVariable Long carId){
        return ResponseEntity.ok(customerService.getBidsByUserId(carId));
    }

    @GetMapping("/car/bid/{bidId}/{status}")
    public ResponseEntity<?> changeBidStatus(@PathVariable Long bidId, @PathVariable String status){
        boolean success = customerService.changeBidStatus(bidId, status);
        if(success) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/car/analytics/{userId}")
    public ResponseEntity<?> getAnaylytics(@PathVariable Long userId){
        return ResponseEntity.ok(customerService.getAnalytics(userId));
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        customerService.deleteCarById(id);
        return ResponseEntity.ok(null);
    }

}

package com.example.carsellservice.service.admin;

import com.example.carsellservice.dto.CarDto;
import com.example.carsellservice.dto.SearchCarDto;
import com.example.carsellservice.entity.Car;
import com.example.carsellservice.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final CarRepository carRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public CarDto getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public List<CarDto> searchCar(SearchCarDto searchCarDto) {
        Car car = new Car();

        car.setType(searchCarDto.getType());
        car.setColor(searchCarDto.getColor());
        car.setBrand(searchCarDto.getBrand());
        car.setTransmission(searchCarDto.getTransmission());

        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll() //A generic property matcher that specifies string mtching and case sensittivity
                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Car> carExample = Example.of(car, exampleMatcher);//this class consists of the probe and the ExampleMatcher and it used to crerate the query
        List<Car> cars = carRepository.findAll(carExample);

        return cars.stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}

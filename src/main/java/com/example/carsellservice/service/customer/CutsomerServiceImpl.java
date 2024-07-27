package com.example.carsellservice.service.customer;

import com.example.carsellservice.dto.CarDto;
import com.example.carsellservice.entity.Car;
import com.example.carsellservice.entity.User;
import com.example.carsellservice.repositories.CarRepository;
import com.example.carsellservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CutsomerServiceImpl implements CustomerService{

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    @Override
    public boolean createCar(CarDto carDto) throws IOException {
        Optional<User> optionalUser = userRepository.findById(carDto.getUserId());

        if (optionalUser.isPresent()) {
            Car car = new Car();
            car.setName(carDto.getName());
            car.setBrand(carDto.getBrand());
            car.setPrice(carDto.getPrice());
            car.setDescription(carDto.getDescription());
            car.setColor(carDto.getColor());
            car.setTransmission(carDto.getTransmission());
            car.setSold(false);//creating a car for sale initially is not sold so it's false
            car.setYear(carDto.getYear());
            car.setImg(carDto.getImg().getBytes());
            car.setUser(optionalUser.get());
            carRepository.save(car);
            return true;
        }
        return false;
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public CarDto getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);//the carRepository return an optional car;
        return optionalCar.map(Car::getCarDto).orElse(null);//the optional is mapped to dto that can be used by the application or if it doesn't exist return a null
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }
}

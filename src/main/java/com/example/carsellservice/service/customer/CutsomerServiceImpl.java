package com.example.carsellservice.service.customer;

import com.example.carsellservice.dto.AnalyticsDto;
import com.example.carsellservice.dto.BidDto;
import com.example.carsellservice.dto.CarDto;
import com.example.carsellservice.dto.SearchCarDto;
import com.example.carsellservice.entity.Bid;
import com.example.carsellservice.entity.Car;
import com.example.carsellservice.entity.User;
import com.example.carsellservice.enums.BidStatus;
import com.example.carsellservice.repositories.BidRepository;
import com.example.carsellservice.repositories.CarRepository;
import com.example.carsellservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CutsomerServiceImpl implements CustomerService{

    private final UserRepository userRepository;

    private final CarRepository carRepository;

    private final BidRepository bidRepository;

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
    public boolean updateCar(Long id, CarDto carDto) throws IOException {
        Optional<Car> OptionalCar = carRepository.findById(id);

        if(OptionalCar.isPresent()) {
            Car car = OptionalCar.get();
            car.setName(carDto.getName());
            car.setBrand(carDto.getBrand());
            car.setPrice(carDto.getPrice());
            car.setDescription(carDto.getDescription());
            car.setColor(carDto.getColor());
            car.setTransmission(carDto.getTransmission());
            car.setSold(false);//creating a car for sale initially is not sold so it's false
            car.setYear(carDto.getYear());
            if (carDto.getImg() != null) car.setImg(carDto.getImg().getBytes());

            carRepository.save(car);

            return true;
        }
        return false;
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
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
    public List<CarDto> getCustomerCars(Long userId) {
        return carRepository.findAllByUserId(userId).stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean bidOnCar(BidDto bidDto) {
        Optional<Car> optionalCar = carRepository.findById(bidDto.getCarId());//this is possible cause of the relationship the bid table has with car and user tables
        Optional<User> optionalUser = userRepository.findById(bidDto.getUserId());

        if(optionalUser.isPresent() && optionalCar.isPresent()){
            Bid bid = new Bid();

            bid.setUser(optionalUser.get());
            bid.setCar(optionalCar.get());
            bid.setBidStatus(BidStatus.PENDING);//initial state of the bid Status
            bid.setPrice(bidDto.getPrice());
            bidRepository.save(bid);

            return true;
        }
        return false;
    }

    @Override
    public List<BidDto> getBidsByUserId(Long userId) {
        return bidRepository.findAllByUserId(userId).stream().map(Bid::getBidDto).collect(Collectors.toList());
    }

    @Override
    public List<BidDto> getBidsByCarId(Long carId) {
        return bidRepository.findAllByCarId(carId).stream().map(Bid::getBidDto).collect(Collectors.toList());
    }

    @Override
    public AnalyticsDto getAnalytics(Long userId) {
        AnalyticsDto analyticsDto = new AnalyticsDto();
        analyticsDto.setSoldCars(carRepository.countByUserIdAndSoldTrue(userId));
        analyticsDto.setSoldCars(carRepository.countByUserId(userId));
        return analyticsDto;
    }

    @Override
    public boolean changeBidStatus(Long bidId, String status) {
        Optional<Bid> optionalBid = bidRepository.findById(bidId);

        if (optionalBid.isPresent()){
            Bid existingBid = optionalBid.get();//this gets the bid

            if(existingBid.getCar().getSold()) return false;//check if it is sold and if it is return false cause the status is already APPROVED

            if(Objects.equals(status, "Approved")){ //test if the status trying to be set is Approved and if it bidStatus is set to Approved
                existingBid.setBidStatus(BidStatus.APPROVED);
            } else{
                existingBid.setBidStatus(BidStatus.REJECTED);
            }
            bidRepository.save(existingBid);//this saves the new bid Status
            return true;
        }
        return false;
    }


}

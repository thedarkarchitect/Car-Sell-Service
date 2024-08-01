package com.example.carsellservice.entity;

import com.example.carsellservice.dto.BidDto;
import com.example.carsellservice.enums.BidStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "car_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    private BidStatus bidStatus;

    public BidDto getBidDto(){
        BidDto bidDto = new BidDto();
        bidDto.setId(id);
        bidDto.setPrice(price);
        bidDto.setCarId(car.getId());
        bidDto.setUserId(user.getId());
        bidDto.setEmail(user.getEmail());
        bidDto.setUsername(user.getUsername());
        bidDto.setCarName(car.getName());
        bidDto.setCarBrand(car.getBrand());
        bidDto.setSellerName(user.getName());
        bidDto.setBidStatus(bidStatus);
        return bidDto;
    }
}

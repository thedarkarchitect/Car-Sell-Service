package com.example.carsellservice.dto;

import com.example.carsellservice.enums.BidStatus;
import lombok.Data;

@Data
public class BidDto {

    private Long id;

    private Long price;

    private BidStatus bidStatus;

    private Long userId;

    private Long carId;

    private String username;

    private String carName;

    private String carBrand;

    private String email;

    private String sellerName;
}

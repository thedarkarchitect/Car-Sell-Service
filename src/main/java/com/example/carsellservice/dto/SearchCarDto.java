package com.example.carsellservice.dto;

import lombok.Data;

@Data
public class SearchCarDto {

    private String brand;

    private String type;

    private String color;

    private String transmission;
}

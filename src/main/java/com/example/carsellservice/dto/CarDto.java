package com.example.carsellservice.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class CarDto {

    private Long id;

    private String name;

    private String brand;

    private String type;

    private String transmission;

    private String color;

    private Date year;

    private Boolean sold;

    private Long price;

    private String description;

    private MultipartFile img;//MultipartFile is a representation of an uploaded file received in a multipart request

    private Long userId;

    private byte[] returnedImg;

}

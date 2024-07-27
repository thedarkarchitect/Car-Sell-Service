package com.example.carsellservice.entity;

import com.example.carsellservice.dto.CarDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
@Table(name = "\"car\"")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    private String type;

    private String transmission;

    private String color;

    private Date year;

    private Boolean sold;

    private Long price;

    @Lob
    private String description;

    @Lob//this is a Large Object type, that a persistent property or field should be persisted as a large object to a database-supported large object type
    @Column(columnDefinition = "BYTEA")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)//the relationship with this entity and the user entity
    @JoinColumn(name = "user_id", nullable = false)//this field will be called user_id and will be the foreign key in this entity
    @OnDelete(action = OnDeleteAction.CASCADE) //when user is deleted
    @JsonIgnore//this makes the foreign key details not to be shown when this entity json is called
    private User user;

    public CarDto getCarDto() {
        CarDto carDto = new CarDto();
        carDto.setId(id);
        carDto.setName(name);
        carDto.setBrand(brand);
        carDto.setType(type);
        carDto.setTransmission(transmission);
        carDto.setColor(color);
        carDto.setYear(year);
        carDto.setSold(sold);
        carDto.setDescription(description);
        carDto.setPrice(price);
        carDto.setReturnedImg(img);
        return carDto;
    }
}

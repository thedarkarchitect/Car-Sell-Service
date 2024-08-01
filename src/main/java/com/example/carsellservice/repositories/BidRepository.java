package com.example.carsellservice.repositories;


import com.example.carsellservice.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findAllByUserId(Long userId);

    List<Bid> findAllByCarId(Long carId);
}

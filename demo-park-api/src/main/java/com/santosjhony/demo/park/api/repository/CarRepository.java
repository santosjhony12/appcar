package com.santosjhony.demo.park.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santosjhony.demo.park.api.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByUsername(String username);

    List<Car> findByAutorizado(boolean b);
}

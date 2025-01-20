package com.santosjhony.demo.park.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.santosjhony.demo.park.api.entity.Car;
import com.santosjhony.demo.park.api.repository.CarRepository;
import com.santosjhony.demo.park.api.web.dto.CarCreateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    @Transactional
    public Car create(CarCreateDto carCreateDto){
        try{
            Car newCar = carCreateDto.toCar(carCreateDto);
            return carRepository.save(newCar);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar objeto carro");
        }
    }

    @Transactional
    public List<Car> getAll(){
        return carRepository.findAll();
    }
    @Transactional
    public List<Car> getByUsername(String username){
        return carRepository.findByUsername(username);
    }
}

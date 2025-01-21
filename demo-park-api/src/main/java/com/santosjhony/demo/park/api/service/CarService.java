package com.santosjhony.demo.park.api.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.santosjhony.demo.park.api.entity.Car;
import com.santosjhony.demo.park.api.exception.EntityNotFoundException;
import com.santosjhony.demo.park.api.repository.CarRepository;
import com.santosjhony.demo.park.api.web.dto.CarCreateDto;
import com.santosjhony.demo.park.api.web.dto.UpdateCarroTaxasEAutorizadoDto;

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
    @Transactional
    public Car findById(Long id){
        
        return carRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro com id não encontrado."));
    }

    @Transactional
    public Car updateTaxasAutorizado(UpdateCarroTaxasEAutorizadoDto carro){
        Car carroEncontrado = findById(carro.id());

        carroEncontrado.setAutorizado(carro.autorizado());
        carroEncontrado.setPercentualInvestidor(carro.percentualInvestidor());
        carroEncontrado.setPercentualMotorista(carro.percentualMotorista());
        carroEncontrado.setPercentualSistema(carro.percentualSistema());
        return carRepository.save(carroEncontrado);
    }
    @Transactional
    public List<Car> getCarrosParaAutorizar(){
        return carRepository.findByAutorizado(false);
    }
}

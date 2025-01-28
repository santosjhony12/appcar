package com.santosjhony.demo.park.api.web.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santosjhony.demo.park.api.entity.Car;
import com.santosjhony.demo.park.api.service.CarService;
import com.santosjhony.demo.park.api.web.dto.CarCreateDto;
import com.santosjhony.demo.park.api.web.dto.UpdateCarroTaxasEAutorizadoDto;

import lombok.RequiredArgsConstructor;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping(value = "api/v1/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping("/create")
    public ResponseEntity<Car> create(@RequestBody CarCreateDto carCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(carCreateDto));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getAll(){
        return ResponseEntity.ok(carService.getAll());
    }

    @GetMapping("/username")
    public ResponseEntity<List<Car>> getByUsername(@Param(value = "username") String username){
        return ResponseEntity.ok(carService.getByUsername(username));
    }
    @PutMapping("/autorizar")
    public ResponseEntity<Car> updateAutorizadoTaxas(@RequestBody UpdateCarroTaxasEAutorizadoDto dto){
        return ResponseEntity.ok(carService.updateTaxasAutorizado(dto));
    }

    @GetMapping("/nao-autorizados")
    public ResponseEntity<List<Car>> getCarrosParaAutorizar(){
        return ResponseEntity.ok(carService.getCarrosParaAutorizar());
    }
    @GetMapping("/veiculos-autorizados")
    public ResponseEntity<List<Car>> getCarrosAutorizados(){
        return ResponseEntity.ok(carService.getCarrosAutorizados());
    }
    
}

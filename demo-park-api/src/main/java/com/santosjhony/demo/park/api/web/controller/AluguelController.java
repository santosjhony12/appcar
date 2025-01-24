package com.santosjhony.demo.park.api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santosjhony.demo.park.api.entity.Aluguel;
import com.santosjhony.demo.park.api.service.AluguelService;
import com.santosjhony.demo.park.api.web.dto.AluguelCreateDto;
import com.santosjhony.demo.park.api.web.dto.VerificarDispAluguel;

import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping("/api/v1/alugueis")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AluguelController {
    private final AluguelService aluguelService;

    @PostMapping
    public ResponseEntity<Aluguel> create(@RequestBody AluguelCreateDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(aluguelService.create(dto));
    }

    @PostMapping("/disponibilidade")
    public ResponseEntity<List<Aluguel>> buscarDisponibilidade(@RequestBody VerificarDispAluguel dto){
        return ResponseEntity.ok(aluguelService.verificarDisponibilidade(dto));
    }

}

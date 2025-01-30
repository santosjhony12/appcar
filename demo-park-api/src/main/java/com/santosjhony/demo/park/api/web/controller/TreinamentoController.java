package com.santosjhony.demo.park.api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santosjhony.demo.park.api.entity.Role;
import com.santosjhony.demo.park.api.entity.Treinamento;
import com.santosjhony.demo.park.api.service.TreinamentoService;
import com.santosjhony.demo.park.api.web.dto.TreinamentoCreateDto;
import com.santosjhony.demo.park.api.web.dto.TreinamentoUpdateDto;

import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequestMapping("/api/v1/treinamentos")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TreinamentoController {
    private final TreinamentoService treinamentoService;

    @PostMapping
    public ResponseEntity<Treinamento> create(@RequestBody TreinamentoCreateDto createDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(treinamentoService.create(createDto));
    }

    @GetMapping
    public ResponseEntity<List<Treinamento>> getAll(){
        return ResponseEntity.ok(treinamentoService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        treinamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Treinamento> update(@RequestBody TreinamentoUpdateDto dto){
        return ResponseEntity.ok(treinamentoService.editar(dto));
    }

    @GetMapping("/role")
    public ResponseEntity<List<Treinamento>> getByRole(@RequestParam("role") Role role){
        return ResponseEntity.ok(treinamentoService.getByRole(role));
    }
}

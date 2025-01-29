package com.santosjhony.demo.park.api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.santosjhony.demo.park.api.entity.Comentario;
import com.santosjhony.demo.park.api.service.ComentarioService;
import com.santosjhony.demo.park.api.web.dto.ComentarioCreateDto;

import lombok.RequiredArgsConstructor;

@RestController
@Controller
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1/comentarios")
public class ComentarioController {
    private final ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<Comentario> create(@RequestBody ComentarioCreateDto comentarioCreateDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioService.create(comentarioCreateDto));
    }
}

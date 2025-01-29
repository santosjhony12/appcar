package com.santosjhony.demo.park.api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santosjhony.demo.park.api.entity.Aluguel;
import com.santosjhony.demo.park.api.service.AluguelService;
import com.santosjhony.demo.park.api.web.dto.AluguelCreateDto;
import com.santosjhony.demo.park.api.web.dto.AluguelResponseDto;
import com.santosjhony.demo.park.api.web.dto.AluguelResponseUsuarioDto;
import com.santosjhony.demo.park.api.web.dto.DadosParaDashDto;
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
    @GetMapping("/aprovar")
    public ResponseEntity<List<AluguelResponseDto>> getAlugueisAprovar(@RequestParam("autorizado") Boolean autorBoolean){
        System.out.println(autorBoolean);
        return ResponseEntity.ok(aluguelService.getAluguelAprovar(autorBoolean));
    }
    @PatchMapping("/aprovar")
    public ResponseEntity<String> aprovarAluguel(@RequestParam("id") Long id){
        aluguelService.autorizarAluguel(id);
        return ResponseEntity.ok("Aluguel aprovado com sucesso!");
    }
    @GetMapping("/aprovacao")
    public ResponseEntity<List<AluguelResponseUsuarioDto>> getByUsuarioLogado(){
        return ResponseEntity.ok(aluguelService.getByUsuario());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        aluguelService.delete(id);

        return ResponseEntity.ok().build();
    }
    @GetMapping("/veiculos-alugados/locador")
    public ResponseEntity<List<AluguelResponseUsuarioDto>> getByLocador(){
        return ResponseEntity.ok(aluguelService.getAlugueisByLocador());
    }

    @GetMapping("/dados-dash-investidor")
    public ResponseEntity<DadosParaDashDto> getDadosParaDash(){
        return ResponseEntity.ok(aluguelService.getDadosParaDash());
    }
}

package com.santosjhony.demo.park.api.web.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioUpdatePrimeiroAcesoDto(
                @NotNull
                Long id,
                @NotBlank
                @CPF 
                String cpf, 
                @NotBlank
                String nome,
                @NotNull 
                LocalDate dataNascimento, 
                @NotBlank
                @Size(min = 6, max = 20)
                String newPassword) {
    
}

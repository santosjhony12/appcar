package com.santosjhony.demo.park.api.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public record AluguelResponseDto(Long id, String username, 
String nomeUsuario, String modeloCarro, 
String placa, Boolean aprovado,  
@JsonFormat(pattern = "yyyy-MM-dd")LocalDate dataInicio, 
 @JsonFormat(pattern = "yyyy-MM-dd")  LocalDate dataFim, BigDecimal valorAluguel) {
    
}

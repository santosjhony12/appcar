package com.santosjhony.demo.park.api.web.dto;

import java.time.LocalDate;

public record VerificarDispAluguel(Long idCarro, LocalDate dataInicio, LocalDate dataFim) {
    
}

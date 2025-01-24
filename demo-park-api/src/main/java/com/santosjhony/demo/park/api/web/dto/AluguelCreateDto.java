package com.santosjhony.demo.park.api.web.dto;

import java.time.LocalDate;

public record AluguelCreateDto(Long idCarro, LocalDate dataInicio, LocalDate dataFim, Long idUsuario) {

}

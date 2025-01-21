package com.santosjhony.demo.park.api.web.dto;

import java.math.BigDecimal;

public record UpdateCarroTaxasEAutorizadoDto(Long id, BigDecimal percentualInvestidor, BigDecimal percentualMotorista, BigDecimal percentualSistema, Boolean autorizado) {
    
}

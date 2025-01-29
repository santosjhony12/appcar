package com.santosjhony.demo.park.api.web.dto;

import com.santosjhony.demo.park.api.entity.Role;

public record TreinamentoUpdateDto(Long id, String titulo, String link, Role role) {
    
}

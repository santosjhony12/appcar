package com.santosjhony.demo.park.api.web.dto;

import com.santosjhony.demo.park.api.jwt.JwtToken;

public record TokenUsuarioDto(JwtToken token, UsuarioResponseDto usuarioResponseDto) {
    
}

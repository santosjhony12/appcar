package com.santosjhony.demo.park.api.web.dto;

import com.santosjhony.demo.park.api.entity.Role;

public record UsuarioCreateDto(

String username,
    Role role

) {
}

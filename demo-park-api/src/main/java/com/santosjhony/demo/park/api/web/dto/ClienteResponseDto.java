package com.santosjhony.demo.park.api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class ClienteResponseDto {
    private Long id;
    private String nome;
    private String cpf;
}

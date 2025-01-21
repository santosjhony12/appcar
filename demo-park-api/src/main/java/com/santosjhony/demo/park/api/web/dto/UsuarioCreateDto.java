package com.santosjhony.demo.park.api.web.dto;

import com.santosjhony.demo.park.api.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioCreateDto {

    @NotBlank
    @Email(message = "Formato do e-mail inválido.")
    private String username;;
    private Role role;
}

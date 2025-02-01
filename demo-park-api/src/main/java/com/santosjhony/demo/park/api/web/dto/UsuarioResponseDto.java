package com.santosjhony.demo.park.api.web.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.santosjhony.demo.park.api.entity.Role;
import com.santosjhony.demo.park.api.entity.Usuario;

public record UsuarioResponseDto(
        Long id,
        String username,
        String nome,
        String cpf,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dataNascimento,
        Role role,
        Boolean primeiroAcesso,
        String telefone,
        Boolean resetSenha

) {

    public static UsuarioResponseDto toDto(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto(usuario.getId(), usuario.getUsername(), usuario.getNome(),
                usuario.getCpf(), usuario.getDataNascimento(),
                usuario.getRole(), usuario.getPrimeiroAcesso(), usuario.getTelefone(), usuario.getResetSenha());

        return dto;
    }

    public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(UsuarioResponseDto::toDto)
                .collect(Collectors.toList());
    }

}

package com.santosjhony.demo.park.api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santosjhony.demo.park.api.entity.Usuario;
import com.santosjhony.demo.park.api.service.UsuarioService;
import com.santosjhony.demo.park.api.web.dto.UpdateRoleDto;
import com.santosjhony.demo.park.api.web.dto.UsuarioCreateDto;
import com.santosjhony.demo.park.api.web.dto.UsuarioResponseDto;
import com.santosjhony.demo.park.api.web.dto.UsuarioUpdatePrimeiroAcesoDto;
import com.santosjhony.demo.park.api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RestController
@RequestMapping("api/v1/usuarios")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UsuarioController {
    private final UsuarioService usuarioService;
    @Operation(
            summary = "Criar um novo usuário.",
            description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistena",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto usuarioCreateDto){
        Usuario user = usuarioService.salvar(usuarioCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponseDto.toDto(user));
    }
    @Operation(
            summary = "Recuperar um usuário por ID.",
            description = "Requisição exige um bearer token. Acesso restrito a ADMIN ou CLIENTE.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('CLIENTE') AND #id == authentication.principal.id)")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioResponseDto.toDto(user));
    }
    @Operation(
            summary = "Atualizar senha.",
            description = "Requisição exige um bearer token. Acesso restrito a ADMIN ou CLIENTE.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha atualizada com sucesso."),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere..",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos invalidos ou mal formatados.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/resetSenha")
    public ResponseEntity<Void> updatePassword(@RequestParam("id") Long id, @RequestParam("senha") String senha){
        usuarioService.editarSenha(id, senha);
        return ResponseEntity.noContent().build();
    }
    @Operation(
            summary = "Recuperar todos os usuários",
            description = "Requisição exige um bearer token. Acesso restrito a ADMIN.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuários recuperados",
                        content = @Content(mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDto>> getAll(){
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioResponseDto.toListDto(users));
    }



    @PostMapping("/updatePrimeiroAcesso")
    public ResponseEntity<UsuarioResponseDto> updatePrimeiroAcesso(@RequestBody UsuarioUpdatePrimeiroAcesoDto dto){
        return ResponseEntity.ok(UsuarioResponseDto.toDto(usuarioService.updateUsuarioPrimeiroAcesso(dto)));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateRole")
    public ResponseEntity<UsuarioResponseDto> updateRole(@RequestBody UpdateRoleDto dto){
        return ResponseEntity.ok(UsuarioResponseDto.toDto(usuarioService.updateRole(dto)));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/enviarEmailReset")
    @PermitAll
    public ResponseEntity<Void> recuperarSenha(@RequestParam("email") String email){
        usuarioService.recurarSenha(email);
        return ResponseEntity.noContent().build();
    }
}

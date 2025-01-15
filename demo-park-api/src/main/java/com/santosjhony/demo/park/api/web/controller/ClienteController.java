package com.santosjhony.demo.park.api.web.controller;

import com.santosjhony.demo.park.api.entity.Cliente;
import com.santosjhony.demo.park.api.jwt.JwtUserDetails;
import com.santosjhony.demo.park.api.repository.projection.ClienteProjection;
import com.santosjhony.demo.park.api.service.ClienteService;
import com.santosjhony.demo.park.api.service.UsuarioService;
import com.santosjhony.demo.park.api.web.dto.ClienteCreateDto;
import com.santosjhony.demo.park.api.web.dto.ClienteResponseDto;
import com.santosjhony.demo.park.api.web.dto.PageableDto;
import com.santosjhony.demo.park.api.web.dto.UsuarioResponseDto;
import com.santosjhony.demo.park.api.web.dto.mapper.ClienteMapper;
import com.santosjhony.demo.park.api.web.dto.mapper.PageableMapper;
import com.santosjhony.demo.park.api.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("api/v1/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;
    private final UsuarioService usuarioService;

    @Operation(
            summary = "Criar um novo cliente.",
            description = "Recurso para criar um novo cliente vinculado a um cadastrado."+
                        "Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "Cliente CPF já cadastrado no sistena",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permitido no perfil de ADMIN.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto,
                                                     @AuthenticationPrincipal JwtUserDetails userDetails){
        Cliente cliente = ClienteMapper.toCliente(dto);
        cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
        clienteService.salvar(cliente);
        return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
    }


    @Operation(
            summary = "Recuperar um cliente por ID.",
            description = "Recurso utilizado para recuperar um cliente pelo ID"+
                    "Requisição exige um bearer token. Acesso restrito a ADMIN.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso recuperado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id){
        Cliente cliente = clienteService.buscarPorId(id);
        return ResponseEntity.ok().body(ClienteMapper.toDto(cliente));
    }
    @Operation(
            summary = "Recuperar todos os clientes",
            description = "Requisição exige um bearer token. Acesso restrito a ADMIN.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in =  ParameterIn.QUERY, name = "page",
                    content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                    description = "Representa a página retornada"),
                    @Parameter(in =  ParameterIn.QUERY, name = "size", hidden = true,
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "Representa o total de elementos por página"),
                    @Parameter(in =  ParameterIn.QUERY, name = "sort",
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,asc")),
                            description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Clientes recuperados",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ClienteResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Usuário sem permissão para acessar esse recurso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAll(@Parameter(hidden = true) @PageableDefault(size = 5, sort = {"nome"}) Pageable pageable){
        Page<ClienteProjection> clientes = clienteService.buscarClientes(pageable);
        return ResponseEntity.ok().body(PageableMapper.toDto(clientes));
    }
    @Operation(
            summary = "Recuperar dados do Cliente",
            description = "Requisição exige um bearer token.",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente recuperado",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class)))),
                    @ApiResponse(responseCode = "403", description = "Cliente sem permissão para acessar esse recurso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/detalhes")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<ClienteResponseDto> getDetails(@AuthenticationPrincipal JwtUserDetails jwtUser){
        Cliente cliente = clienteService.buscarUsuarioPorId(jwtUser.getId());
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }
}

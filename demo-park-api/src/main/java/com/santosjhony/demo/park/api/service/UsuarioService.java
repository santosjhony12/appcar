package com.santosjhony.demo.park.api.service;

import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.santosjhony.demo.park.api.entity.Role;
import com.santosjhony.demo.park.api.entity.Usuario;
import com.santosjhony.demo.park.api.exception.EntityNotFoundException;
import com.santosjhony.demo.park.api.exception.PasswordInvalidException;
import com.santosjhony.demo.park.api.exception.UsernameUniqueViolationException;
import com.santosjhony.demo.park.api.repository.UsuarioRepository;
import com.santosjhony.demo.park.api.web.dto.UpdateRoleDto;
import com.santosjhony.demo.park.api.web.dto.UsuarioUpdatePrimeiroAcesoDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        try {
            String code = generateConfirmationCode();
            usuario.setPassword(passwordEncoder.encode(code));

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(usuario.getUsername());
            message.setSubject("Bem vindo a plataforma App Car!!");
            message.setText("Entre com as seguintes informações para obter acesso a plataforma:\n\nE-mail: "+usuario.getUsername()+"\nSenha: "+code);
            mailSender.send(message);
            return usuarioRepository.save(usuario);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username '%s' já cadastrado", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
    }

    @Transactional
    public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
        if (!novaSenha.equals(confirmaSenha)) {
            throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
        }

        Usuario user = buscarPorId(id);
        if (!passwordEncoder.matches(senhaAtual, user.getPassword())) {
            throw new PasswordInvalidException("Sua senha não confere.");
        }

        user.setPassword(passwordEncoder.encode(novaSenha));
        return user;
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username))
        );
    }

    @Transactional(readOnly = true)
    public Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }

    @Transactional
    public Usuario updateUsuarioPrimeiroAcesso(UsuarioUpdatePrimeiroAcesoDto dto){
        try{
            Usuario usuario = buscarPorId(dto.id());
            usuario.setCpf(dto.cpf());
            usuario.setNome(dto.nome());
            usuario.setDataNascimento(dto.dataNascimento());
            usuario.setPrimeiroAcesso(false);

            return usuarioRepository.save(usuario);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao tentar atualizar usuário");
        }
    }

    @Transactional
    public Usuario updateRole(UpdateRoleDto dto){
        Usuario usuario = buscarPorId(dto.id());
        
        usuario.setRole(dto.role());
        return usuarioRepository.save(usuario);
    }
    @Transactional
    public void deleteUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    private String generateConfirmationCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}

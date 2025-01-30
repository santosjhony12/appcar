package com.santosjhony.demo.park.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santosjhony.demo.park.api.entity.Comentario;
import com.santosjhony.demo.park.api.entity.Treinamento;
import com.santosjhony.demo.park.api.repository.ComentarioRepository;
import com.santosjhony.demo.park.api.web.dto.ComentarioCreateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComentarioService {
    private final ComentarioRepository comentarioRepository;
    private final TreinamentoService treinamentoService;
    public Comentario create(ComentarioCreateDto dto){
        Comentario comentario = new Comentario();
        comentario.setUsername(dto.username());
        comentario.setComentario(dto.comentario());
        Treinamento treinamento = treinamentoService.findById(dto.idTreinamento());
        comentario.setTreinamento(treinamento);

        return comentarioRepository.save(comentario);
    }

    @Transactional
    public List<Comentario> getByTreinamento(Long id){
        Treinamento treinamento = treinamentoService.findById(id);
        return comentarioRepository.findByTreinamento(treinamento);
    }
}   

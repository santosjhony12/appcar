package com.santosjhony.demo.park.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santosjhony.demo.park.api.entity.Treinamento;
import com.santosjhony.demo.park.api.repository.TreinamentoRepository;
import com.santosjhony.demo.park.api.web.dto.TreinamentoCreateDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TreinamentoService {
    private final TreinamentoRepository treinamentoRepository;

    @Transactional
    public Treinamento create(TreinamentoCreateDto dto){

        Treinamento treinamento = new Treinamento();
        treinamento.setTitulo(dto.titulo());
        treinamento.setLink(dto.link());
        treinamento.setRole(dto.role());
        return treinamentoRepository.save(treinamento);
    }

    @Transactional
    public List<Treinamento> getAll(){
        return treinamentoRepository.findAll();
    }
    @Transactional
    public void delete(Long id){
        treinamentoRepository.deleteById(id);
    }
}

package com.santosjhony.demo.park.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santosjhony.demo.park.api.entity.Aluguel;
import com.santosjhony.demo.park.api.entity.Car;
import com.santosjhony.demo.park.api.exception.EntityNotFoundException;
import com.santosjhony.demo.park.api.repository.AluguelRepository;
import com.santosjhony.demo.park.api.web.dto.AluguelCreateDto;
import com.santosjhony.demo.park.api.web.dto.VerificarDispAluguel;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AluguelService {
    private final AluguelRepository aluguelRepository;
    private final CarService carService;
    private final UsuarioService usuarioService;
    @Transactional
    public Aluguel create(AluguelCreateDto dto){
        Aluguel aluguel = new Aluguel();
        aluguel.setDataFim(dto.dataFim());
        aluguel.setDataInicio(dto.dataInicio());
        Car carro = carService.findById(dto.idCarro());
        aluguel.setCarro(carro);
        aluguel.setUsuario(usuarioService.buscarPorId(dto.idUsuario()));
        aluguel.setValor(carro.getValor());

        return aluguelRepository.save(aluguel);
    }

    @Transactional
    public List<Aluguel> verificarDisponibilidade(VerificarDispAluguel dto){
        Car carro = carService.findById(dto.idCarro());

        List<Aluguel> aluguel = aluguelRepository.verificarConflitoAluguel(carro.getId(), dto.dataInicio(), dto.dataFim());

        if(aluguel.isEmpty()){
            throw new EntityNotFoundException("Veículo disponível para a data selecionada.");

            
        }else{
            return aluguel;
        }
    }
}

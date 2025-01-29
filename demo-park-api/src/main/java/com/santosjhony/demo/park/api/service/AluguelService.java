package com.santosjhony.demo.park.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santosjhony.demo.park.api.entity.Aluguel;
import com.santosjhony.demo.park.api.entity.Car;
import com.santosjhony.demo.park.api.entity.Usuario;
import com.santosjhony.demo.park.api.exception.EntityNotFoundException;
import com.santosjhony.demo.park.api.repository.AluguelRepository;
import com.santosjhony.demo.park.api.web.dto.AluguelCreateDto;
import com.santosjhony.demo.park.api.web.dto.AluguelResponseDto;
import com.santosjhony.demo.park.api.web.dto.AluguelResponseUsuarioDto;
import com.santosjhony.demo.park.api.web.dto.DadosParaDashDto;
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
        aluguel.setValor(dto.valor());

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

    @Transactional
    public List<AluguelResponseDto> getAluguelAprovar(Boolean autorBoolean) {
        List<Aluguel> alugueis = aluguelRepository.findByAprovadoOrderByDataInicio(autorBoolean);
        System.out.println(alugueis);
        List<AluguelResponseDto> alugueisResponse = alugueis.stream()
        .map(aluguel -> {
            AluguelResponseDto aluguelResponse = new AluguelResponseDto(
                aluguel.getId(), aluguel.getUsuario().getUsername(), aluguel.getUsuario().getNome(), 
                aluguel.getCarro().getModelo(), aluguel.getCarro().getPlaca(), autorBoolean, aluguel.getDataInicio(), aluguel.getDataFim(), aluguel.getValor());

                return aluguelResponse;
        }).collect(Collectors.toList());

        return alugueisResponse;
    }

    @Transactional
    public Aluguel findById(Long id){
        return aluguelRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluguel com id descrito não encontrado"));
    }

    @Transactional
    public Aluguel autorizarAluguel(Long id){
        Aluguel aluguel = findById(id);
        aluguel.setAprovado(true);
        aluguel = aluguelRepository.save(aluguel);
        return aluguel;
    }

    @Transactional
    public List<AluguelResponseUsuarioDto> getByUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);
        
        List<Aluguel> alugueisEncontrados = aluguelRepository.findByUsuarioOrderByDataInicio(usuario);
        
        List<AluguelResponseUsuarioDto> aluguelResponseUsuarioDtos = alugueisEncontrados.stream()
        .map(aluguel -> {
            AluguelResponseUsuarioDto aluguelResponse = new AluguelResponseUsuarioDto(
            aluguel.getId(),
             aluguel.getUsuario().getUsername(), 
             aluguel.getUsuario().getNome(), 
            aluguel.getCarro().getModelo(), 
            aluguel.getCarro().getPlaca(), 
            aluguel.getAprovado(), 
            aluguel.getDataInicio(), 
            aluguel.getDataFim(), 
            aluguel.getValor(), 
            aluguel.getCarro().getImagem()
            );
            
            return aluguelResponse;
        }).collect(Collectors.toList());

        return aluguelResponseUsuarioDtos;
    }

    @Transactional
    public void delete(Long id) {
        aluguelRepository.findById(id);
        aluguelRepository.deleteById(id);
    }

    @Transactional
    public List<AluguelResponseUsuarioDto> getAlugueisByLocador(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Aluguel> alugueis = aluguelRepository.findByLocatario(username);

        List<AluguelResponseUsuarioDto> aluguelResponseUsuarioDtos = alugueis.stream()
        .map(aluguel -> {

            long diferencaDias = ChronoUnit.DAYS.between(aluguel.getDataInicio(), aluguel.getDataFim());
            BigDecimal percentualInvestidor = aluguel.getCarro().getPercentualInvestidor();
            BigDecimal percentualDecimal = percentualInvestidor.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            System.out.println(diferencaDias);
            AluguelResponseUsuarioDto aluguelResponse = new AluguelResponseUsuarioDto(
            aluguel.getId(),
             aluguel.getUsuario().getUsername(), 
             aluguel.getUsuario().getNome(), 
            aluguel.getCarro().getModelo(), 
            aluguel.getCarro().getPlaca(), 
            aluguel.getAprovado(), 
            aluguel.getDataInicio(), 
            aluguel.getDataFim(), 
            aluguel.getValor().multiply(BigDecimal.valueOf(diferencaDias)).multiply(percentualDecimal), 
            aluguel.getCarro().getImagem()
            );
            
            return aluguelResponse;
        }).collect(Collectors.toList());

        return aluguelResponseUsuarioDtos;
    }

    @Transactional
    public DadosParaDashDto getDadosParaDash(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Aluguel> alugueis = aluguelRepository.findByLocatario(username);

        BigDecimal somaValores = alugueis.stream()
        .map(aluguel -> {
            long diferencaDias = ChronoUnit.DAYS.between(aluguel.getDataInicio(), aluguel.getDataFim());
            BigDecimal percentualInvestidor = aluguel.getCarro().getPercentualInvestidor();
            BigDecimal percentualDecimal = percentualInvestidor.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);

            return aluguel.getValor()
                .multiply(BigDecimal.valueOf(diferencaDias))
                .multiply(percentualDecimal);
        })
        .reduce(BigDecimal.ZERO, BigDecimal::add);


        List<Car> carros = carService.getByUsername(username);
        BigDecimal valorInvestido = carros.stream()
        .map(carro -> carro.getValor())
        .filter(Objects::nonNull)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

        DadosParaDashDto dados = new DadosParaDashDto(alugueis.size(), somaValores, valorInvestido);

        return dados;
    }
    
}

package com.santosjhony.demo.park.api.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santosjhony.demo.park.api.entity.Aluguel;


@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    @Query("SELECT a FROM Aluguel a WHERE a.carro.id = :carroId AND (a.dataInicio <= :dataFim AND a.dataFim >= :dataInicio)")
    List<Aluguel> verificarConflitoAluguel(
        @Param("carroId") Long carroId,
        @Param("dataInicio") LocalDate dataInicio,
        @Param("dataFim") LocalDate dataFim
    );
    

}


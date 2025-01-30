package com.santosjhony.demo.park.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santosjhony.demo.park.api.entity.Comentario;
import com.santosjhony.demo.park.api.entity.Treinamento;
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

    List<Comentario> findByTreinamento(Treinamento treinamento);
    
}

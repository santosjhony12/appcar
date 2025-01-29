package com.santosjhony.demo.park.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santosjhony.demo.park.api.entity.Comentario;
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long>{
    
}

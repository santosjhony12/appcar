package com.santosjhony.demo.park.api.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.santosjhony.demo.park.api.entity.Role;
import com.santosjhony.demo.park.api.entity.Treinamento;

@Repository
public interface TreinamentoRepository extends JpaRepository<Treinamento, Long> {

    List<Treinamento> findByRole(Role role);
    
}

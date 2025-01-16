package com.santosjhony.demo.park.api.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {  
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "modelo", length = 50)
    private String modelo;
    @Column(name = "placa", length = 15)
    private String placa;
    @Column(name = "cor", length = 50)
    private String cor;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "valor", precision = 10, scale = 2)
    private BigDecimal valor;
    @Column(name = "montadora", length = 50)
    private String montadora;
    @Lob
    @Column(name = "imagem")
    private byte[] imagem;
}

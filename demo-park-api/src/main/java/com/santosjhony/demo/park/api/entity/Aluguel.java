package com.santosjhony.demo.park.api.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "aluguel") // Defina explicitamente o nome da tabela, se necessário.
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id", nullable = false)
    private Car carro;
    @JsonIgnore 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "aprovado", nullable = false)
    private Boolean aprovado = false;

    @Column(name = "valor_aluguel", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;
}

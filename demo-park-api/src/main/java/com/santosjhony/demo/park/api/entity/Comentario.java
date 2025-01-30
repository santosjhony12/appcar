package com.santosjhony.demo.park.api.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comentarios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comentario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "comentario", length = 2088)
    private String comentario;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Treinamento treinamento;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "dia_comentado")
    private LocalDateTime dayDateTime = LocalDateTime.now();
}

package com.santosjhony.demo.park.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "treinamentos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Treinamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo", length=255)
    private String titulo;
    @Column(name = "link_treinamento", length = 2048)
    private String link;
    @Column(name = "acesso")
    private Role role;    
}

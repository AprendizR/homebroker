package com.renan.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "endereco")

public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cep", length = 9)
    private String cep;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "rua")
    private String rua;

    @Column(name = "usuario_id")
    private Long usuario_id;

}

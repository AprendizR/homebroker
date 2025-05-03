package com.renan.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "telefone")

public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ddd")
    private String ddd;

    @Column(name = "numero")
    private String numero;

    @Column(name = "usuario_id")
    private Long usuario_id;


}

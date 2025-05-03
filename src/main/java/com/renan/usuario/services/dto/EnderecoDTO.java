package com.renan.usuario.services.dto;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class EnderecoDTO {

    private Long id;
    private String cep;
    private String cidade;
    private String uf;
    private String bairro;
    private String rua;

}

package com.renan.usuario.services.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TelefoneDTO {

    private Long id;
    private String ddd;
    private String numero;

}

package com.renan.usuario.services.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UsuarioDTO {

    private String nome;
    private String email;
    private String cpf;
    private String senha;
    private List<TelefoneDTO> telefones;
    private List<EnderecoDTO> enderecos;
}

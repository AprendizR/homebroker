package com.renan.usuario.services.converter;

import com.renan.usuario.infrastructure.entity.Endereco;
import com.renan.usuario.infrastructure.entity.Telefone;
import com.renan.usuario.infrastructure.entity.Usuario;
import com.renan.usuario.services.dto.EnderecoDTO;
import com.renan.usuario.services.dto.TelefoneDTO;
import com.renan.usuario.services.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component

public class UsuarioConverter {

    public Usuario paraUsuario(UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .cpf(usuarioDTO.getCpf())
                .senha(usuarioDTO.getSenha())
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTO){
        return telefoneDTO.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO){
        return Telefone.builder()
                .id(telefoneDTO.getId())
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTO){
        return enderecoDTO.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .id(enderecoDTO.getId())
                .cep(enderecoDTO.getCep())
                .cidade(enderecoDTO.getCidade())
                .uf(enderecoDTO.getUf())
                .bairro(enderecoDTO.getBairro())
                .rua(enderecoDTO.getRua())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(Usuario usuarioDTO){
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .cpf(usuarioDTO.getCpf())
                .senha(usuarioDTO.getSenha())
                .telefones(paraListaTelefoneDTO(usuarioDTO.getTelefones()))
                .enderecos(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneDTO){
        return telefoneDTO.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone){
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero()).build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTO){
        return enderecoDTO.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .bairro(endereco.getBairro())
                .rua(endereco.getRua()).build();
    }
}

package com.renan.usuario.services;

import com.renan.usuario.infrastructure.entity.Endereco;
import com.renan.usuario.infrastructure.entity.Telefone;
import com.renan.usuario.infrastructure.entity.Usuario;
import com.renan.usuario.infrastructure.exceptions.ConflictExceptions;
import com.renan.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.renan.usuario.infrastructure.repository.EnderecoRepository;
import com.renan.usuario.infrastructure.repository.TelefoneRepository;
import com.renan.usuario.infrastructure.repository.UsuarioRepository;
import com.renan.usuario.infrastructure.security.JwtUtil;
import com.renan.usuario.services.converter.UsuarioConverter;
import com.renan.usuario.services.dto.EnderecoDTO;
import com.renan.usuario.services.dto.TelefoneDTO;
import com.renan.usuario.services.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioConverter usuarioConverter;
    private final JwtUtil jwtUtil;

    public void emailExiste(String email) {

        if (verificaEmailExistente(email)) {
            throw new ConflictExceptions("Email já cadastrado" + email);
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO editarUsuario(String token, UsuarioDTO usuarioDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado. " + email));
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                    () -> new ResourceNotFoundException("Email não encontrado: " + email)));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

    public void deletarUsuario(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public EnderecoDTO editarEndereco(Long idEndereco, EnderecoDTO dto) {
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado. " + idEndereco));
        Endereco enderecoAtualizado = usuarioConverter.updateEndereco(dto, entity);
        enderecoAtualizado.setUsuario_id(entity.getUsuario_id());
        Endereco enderecoSalvo = enderecoRepository.save(enderecoAtualizado);
        return usuarioConverter.paraEnderecoDTO(enderecoSalvo);
    }

    public EnderecoDTO criarEndereco(String token, EnderecoDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado. " + email));
        Endereco endereco = usuarioConverter.enderecoEntity(dto, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO editarTelefone(Long idTelefone, TelefoneDTO dto) {
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado. " + idTelefone));
        Telefone telefoneAtualizado = usuarioConverter.updateTelefone(dto, entity);
        telefoneAtualizado.setUsuario_id(entity.getUsuario_id());
        Telefone telefoneSalvo = telefoneRepository.save(telefoneAtualizado);
        return usuarioConverter.paraTelefoneDTO(telefoneSalvo);
    }

    public TelefoneDTO criaTelefone(String token, TelefoneDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado. " + email));
        Telefone telefone = usuarioConverter.telefoneEntity(dto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}

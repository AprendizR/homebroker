package com.renan.usuario.services;

import com.renan.usuario.infrastructure.entity.Usuario;
import com.renan.usuario.infrastructure.exceptions.ConflictExceptions;
import com.renan.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.renan.usuario.infrastructure.repository.UsuarioRepository;
import com.renan.usuario.infrastructure.security.JwtUtil;
import com.renan.usuario.services.converter.UsuarioConverter;
import com.renan.usuario.services.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
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

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
    public UsuarioDTO editaUsuario(String token, UsuarioDTO usuarioDTO){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado. "));
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()): null);
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}

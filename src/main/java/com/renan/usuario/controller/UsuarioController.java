package com.renan.usuario.controller;

import com.renan.usuario.infrastructure.security.JwtUtil;
import com.renan.usuario.services.UsuarioService;
import com.renan.usuario.services.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/usuario")
@RequiredArgsConstructor

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    @PostMapping ("/login")
    public String login(@RequestBody UsuarioDTO dto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        return jwtUtil.generateToken(authentication.getName());
    }


    @PutMapping
    public ResponseEntity<UsuarioDTO> editaUsuario(@RequestHeader ("Authorization") String token, @RequestBody UsuarioDTO dto){
        return ResponseEntity.ok(usuarioService.editaUsuario(token, dto));
    }
}

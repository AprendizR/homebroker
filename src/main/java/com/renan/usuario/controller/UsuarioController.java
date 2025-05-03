package com.renan.usuario.controller;

import com.renan.usuario.infrastructure.entity.Usuario;
import com.renan.usuario.infrastructure.security.JwtUtil;
import com.renan.usuario.services.UsuarioService;
import com.renan.usuario.services.dto.EnderecoDTO;
import com.renan.usuario.services.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscaUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> editarUsuario(@RequestHeader("Authorization") String token, @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.editarUsuario(token, dto));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable String email) {
        usuarioService.deletarUsuario(email);
        return ResponseEntity.ok().build();
    }


    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> editarEndereco(@RequestParam("id") Long id, @RequestBody EnderecoDTO dto) {
        return ResponseEntity.ok(usuarioService.editarEndereco(id, dto));
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> criarEndereco(@RequestHeader("Authorization") String token, @RequestBody EnderecoDTO dto) {
        return ResponseEntity.ok(usuarioService.criarEndereco(token, dto));
    }
}

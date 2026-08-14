package com.scrimet.agendadorhorario.controllers;

import com.scrimet.agendadorhorario.infrainstructure.entities.Usuario;
import com.scrimet.agendadorhorario.infrainstructure.repositories.UsuarioRepository;
import com.scrimet.agendadorhorario.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // DTO simples para receber os dados de login na requisição
    public record LoginDto(String email, String senha) {}

    // DTO simples para registrar um usuário de teste rapidamente
    public record RegistroDto(String email, String senha) {}

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);


        var token = tokenService.gerarToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegistroDto data) {
        if (this.usuarioRepository.findByEmail(data.email()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        String senhaCriptografada = passwordEncoder.encode(data.senha());
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(data.email());
        novoUsuario.setSenha(senhaCriptografada);
        novoUsuario.setRole("ROLE_USER");

        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}
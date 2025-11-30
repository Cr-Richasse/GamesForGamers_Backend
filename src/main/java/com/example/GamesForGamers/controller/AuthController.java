package com.example.GamesForGamers.controller;

import com.example.GamesForGamers.dto.LoginRequest;
import com.example.GamesForGamers.dto.LoginResponse;
import com.example.GamesForGamers.model.Usuario;
import com.example.GamesForGamers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        // 1. Buscar si el usuario existe por email
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Credenciales inválidas (Usuario no encontrado)");
        }

        Usuario usuario = usuarioOpt.get();

        // 2. Verificar la contraseña
        // passwordEncoder.matches(textoPlano, textoEncriptadoEnBD)
        if (passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {

            // 3. Si coincide, devolvemos éxito y datos seguros (sin password)
            return ResponseEntity.ok(new LoginResponse(
                    "Login exitoso",
                    usuario.getNombreUsuario(),
                    usuario.getPermisosAdmin(),
                    usuario.getId()
            ));
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas (Contraseña incorrecta)");
        }
    }
}
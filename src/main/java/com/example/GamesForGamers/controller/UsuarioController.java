package com.example.GamesForGamers.controller;

import com.example.GamesForGamers.model.Usuario;
import com.example.GamesForGamers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder; // <--- NUEVO IMPORT
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // <--- INYECTAMOS EL ENCRIPTADOR

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // --- REGISTRO DE USUARIO ---
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        // 1. Encriptamos la contraseña antes de guardar
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordEncriptada);

        // 2. Asignamos valores por defecto si faltan (para evitar nulos)
        if (usuario.getPermisosAdmin() == null) {
            usuario.setPermisosAdmin(false);
        }

        // 3. Guardamos en BD
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario detalles) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();

            usuarioExistente.setNombreUsuario(detalles.getNombreUsuario());
            usuarioExistente.setEmail(detalles.getEmail());

            // Lógica especial para Password: Solo encriptar si viene una nueva
            if (detalles.getPassword() != null && !detalles.getPassword().isEmpty()) {
                String nuevaPassEncriptada = passwordEncoder.encode(detalles.getPassword());
                usuarioExistente.setPassword(nuevaPassEncriptada);
            }

            usuarioExistente.setNombre(detalles.getNombre());
            usuarioExistente.setRut(detalles.getRut());
            usuarioExistente.setDireccion(detalles.getDireccion());
            usuarioExistente.setCiudad(detalles.getCiudad());
            usuarioExistente.setRegion(detalles.getRegion());
            usuarioExistente.setComuna(detalles.getComuna());

            // Solo actualizamos permisos si se envían explícitamente (seguridad)
            if (detalles.getPermisosAdmin() != null) {
                usuarioExistente.setPermisosAdmin(detalles.getPermisosAdmin());
            }

            return ResponseEntity.ok(usuarioRepository.save(usuarioExistente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
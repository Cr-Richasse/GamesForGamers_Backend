package com.example.GamesForGamers.controller;

import com.example.GamesForGamers.model.Usuario;
import com.example.GamesForGamers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario detalles) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();

            usuarioExistente.setNombreUsuario(detalles.getNombreUsuario());
            usuarioExistente.setEmail(detalles.getEmail());
            usuarioExistente.setPassword(detalles.getPassword());
            usuarioExistente.setNombre(detalles.getNombre());
            usuarioExistente.setRut(detalles.getRut());
            usuarioExistente.setDireccion(detalles.getDireccion());
            usuarioExistente.setCiudad(detalles.getCiudad());
            usuarioExistente.setRegion(detalles.getRegion());
            usuarioExistente.setComuna(usuarioExistente.getNombreUsuario());
            usuarioExistente.setPermisosAdmin(detalles.getPermisosAdmin());

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

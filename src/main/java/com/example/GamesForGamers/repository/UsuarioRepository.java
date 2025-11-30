package com.example.GamesForGamers.repository;

import com.example.GamesForGamers.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Agregamos este método mágico. Spring JPA creará la consulta SQL automáticamente.
    Optional<Usuario> findByEmail(String email);
}
package com.example.GamesForGamers.repository;

import com.example.GamesForGamers.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {

}

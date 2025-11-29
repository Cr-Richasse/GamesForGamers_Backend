package com.example.GamesForGamers.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "videojuegos")
public class Videojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String creador;
    @Column(nullable = false)
    private Double valoracion;
    @Column(nullable = false)
    private Integer precio;
    @Column(nullable = false)
    private Integer descuento;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private String plataforma;
    @Column(nullable = false)
    private String imagenUrl;
    @Column(nullable = false)
    private Integer stock;
}

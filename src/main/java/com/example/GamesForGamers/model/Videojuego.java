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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String descripcion;


    @Column(nullable = false)
    private String creador;


    @Column(nullable = false)
    private Double valoracion = 5.0;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer descuento = 0;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = false)
    private String plataforma;

    @Column(nullable = false)
    private String imagenUrl;

    @Column(nullable = false)
    private Integer stock;
}
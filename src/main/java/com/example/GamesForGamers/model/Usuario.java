package com.example.GamesForGamers.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombreUsuario;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String rut;
    @Column(nullable = false)
    private String direccion;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false)
    private String region;
    @Column(nullable = false)
    private String comuna;
    @Column(nullable = false)
    private Boolean permisosAdmin;





}

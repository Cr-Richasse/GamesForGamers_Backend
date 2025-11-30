package com.example.GamesForGamers.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // <--- IMPORTAR
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_orden")
@Data
public class DetalleOrden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    @JsonIgnore // <--- AGREGAR ESTO: Evita el bucle infinito al convertir a JSON
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "videojuego_id")
    private Videojuego videojuego;

    private Integer cantidad;
    private BigDecimal precioUnitario;
}
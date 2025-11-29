package com.example.GamesForGamers.dto;

import lombok.Data;

@Data
public class VideojuegoDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer discount;
    private String category;
    private String image;

    private String description;
    private String genero;
    private String creador;
    private String plataformas;
    private Double valoracion;
    private Integer stock;
}

package com.example.GamesForGamers.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrdenRequest {
    private Long usuarioId; // Quién compra
    private List<ItemPedido> items; // Qué compra

    @Data
    public static class ItemPedido {
        private Long videojuegoId;
        private Integer cantidad;
    }
}
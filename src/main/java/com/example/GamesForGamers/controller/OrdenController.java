package com.example.GamesForGamers.controller;

import com.example.GamesForGamers.dto.OrdenRequest;
import com.example.GamesForGamers.model.*;
import com.example.GamesForGamers.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort; // Importante para ordenar por fecha
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenRepository ordenRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private VideojuegoRepository videojuegoRepository;

    @GetMapping("/todas")
    public List<Orden> obtenerTodasLasOrdenes() {
        return ordenRepository.findAll(Sort.by(Sort.Direction.DESC, "fecha"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orden> obtenerOrdenPorId(@PathVariable Long id) {
        return ordenRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Orden> obtenerOrdenesPorUsuario(@PathVariable Long usuarioId) {
        return ordenRepository.findByUsuarioId(usuarioId);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> crearOrden(@RequestBody OrdenRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Orden orden = new Orden();
        orden.setUsuario(usuario);
        orden.setFecha(LocalDateTime.now());
        orden.setEstado("COMPLETADA");

        BigDecimal totalOrden = BigDecimal.ZERO;
        List<DetalleOrden> detalles = new ArrayList<>();

        for (OrdenRequest.ItemPedido item : request.getItems()) {
            Videojuego juego = videojuegoRepository.findById(item.getVideojuegoId())
                    .orElseThrow(() -> new RuntimeException("Juego no encontrado"));

            DetalleOrden detalle = new DetalleOrden();
            detalle.setOrden(orden);
            detalle.setVideojuego(juego);
            detalle.setCantidad(item.getCantidad());
            BigDecimal precio = BigDecimal.valueOf(juego.getPrecio());
            detalle.setPrecioUnitario(precio);

            totalOrden = totalOrden.add(precio.multiply(BigDecimal.valueOf(item.getCantidad())));

            detalles.add(detalle);
        }

        orden.setTotal(totalOrden);
        orden.setDetalles(detalles);

        return ResponseEntity.ok(ordenRepository.save(orden));
    }
}
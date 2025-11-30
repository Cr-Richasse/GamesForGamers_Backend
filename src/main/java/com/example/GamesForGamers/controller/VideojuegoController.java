package com.example.GamesForGamers.controller;

import com.example.GamesForGamers.dto.VideojuegoDto;
import com.example.GamesForGamers.model.Videojuego;
import com.example.GamesForGamers.repository.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/videojuegos")
@CrossOrigin(origins = "http://localhost:5173")
public class VideojuegoController {

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    private VideojuegoDto convertToDto(Videojuego entity) {
        VideojuegoDto dto = new VideojuegoDto();

        dto.setId(entity.getId());
        dto.setStock(entity.getStock());
        dto.setName(entity.getTitulo());
        dto.setPrice(entity.getPrecio());
        dto.setCategory(entity.getCategoria());
        dto.setImage(entity.getImagenUrl());
        dto.setDescription(entity.getDescripcion());
        dto.setDiscount(entity.getDescuento());
        dto.setCreador(entity.getCreador());
        dto.setValoracion(entity.getValoracion());
        dto.setPlataformas(entity.getPlataforma());
        dto.setGenero(entity.getCategoria());

        return dto;
    }

    @GetMapping
    public List<VideojuegoDto> getAllVideojuegos() {
        List<Videojuego> juegos = videojuegoRepository.findAll();
        return juegos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideojuegoDto> getVideojuegoById(@PathVariable Long id) {
        return videojuegoRepository.findById(id)
                .map(juego -> ResponseEntity.ok(convertToDto(juego)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Videojuego createVideojuego(@RequestBody Videojuego videojuego) {
        return videojuegoRepository.save(videojuego);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> updateVideojuego(@PathVariable Long id, @RequestBody Videojuego detalles) {
        Optional<Videojuego> videojuegoOptional = videojuegoRepository.findById(id);

        if (videojuegoOptional.isPresent()) {
            Videojuego juegoExistente = videojuegoOptional.get();

            juegoExistente.setTitulo(detalles.getTitulo());
            juegoExistente.setDescripcion(detalles.getDescripcion());
            juegoExistente.setPrecio(detalles.getPrecio());
            juegoExistente.setCategoria(detalles.getCategoria());
            juegoExistente.setStock(detalles.getStock());
            juegoExistente.setImagenUrl(detalles.getImagenUrl());
            juegoExistente.setCreador(detalles.getCreador());
            juegoExistente.setValoracion(detalles.getValoracion());
            juegoExistente.setDescuento(detalles.getDescuento());
            juegoExistente.setPlataforma(detalles.getPlataforma());

            return ResponseEntity.ok(videojuegoRepository.save(juegoExistente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideojuego(@PathVariable Long id) {
        if (videojuegoRepository.existsById(id)) {
            videojuegoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

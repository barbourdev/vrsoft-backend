package com.vrsoft.pedidos.controller;

import com.vrsoft.pedidos.dto.PedidoDTO;
import com.vrsoft.pedidos.service.PedidosService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pedidos")
public class PedidosController {

    private final PedidosService service;

    public PedidosController(PedidosService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoDTO dto) {
        PedidoDTO created = service.criarPedido(dto);
        // 202 Accepted conforme o enunciado
        return ResponseEntity.accepted().body(created);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<String> status(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getStatus(id));
    }
}

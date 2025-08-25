package com.vrsoft.pedidos.repository;

import com.vrsoft.pedidos.entity.Pedido;
import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository {
    void save(Pedido pedido);
    Optional<Pedido> findById(UUID id);
    void updateStatus(UUID id, String status);
    String getStatus(UUID id);
}
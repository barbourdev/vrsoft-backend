package com.vrsoft.pedidos.repository;

import com.vrsoft.pedidos.entity.Pedido;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryPedidoRepository implements PedidoRepository {

    private final Map<UUID, Pedido> memoria = new ConcurrentHashMap<>();

    @Override
    public void save(Pedido pedido) {
        memoria.put(pedido.getId(), pedido);
    }

    @Override
    public Optional<Pedido> findById(UUID id) {
        return Optional.ofNullable(memoria.get(id));
    }

    @Override
    public void updateStatus(UUID id, String status) {
        Optional.ofNullable(memoria.get(id)).ifPresent(p -> p.setStatus(status));
    }

    @Override
    public String getStatus(UUID id) {
        return Optional.ofNullable(memoria.get(id)).map(Pedido::getStatus).orElse("NAO_ENCONTRADO");
    }
}
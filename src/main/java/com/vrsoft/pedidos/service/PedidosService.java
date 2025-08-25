package com.vrsoft.pedidos.service;

import com.vrsoft.pedidos.dto.PedidoDTO;
import com.vrsoft.pedidos.dto.StatusPedidoDTO;
import com.vrsoft.pedidos.entity.Pedido;
import com.vrsoft.pedidos.repository.PedidoRepository;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class PedidosService {

    private final PedidoRepository repo;
    private final RabbitTemplate rabbitTemplate;
    private final Random numberRandom = new Random();

    @Value("${app.queues.entrada}")
    private String entradaQueue;

    @Value("${app.queues.statusSucesso}")
    private String statusSucessoQueue;

    @Value("${app.queues.statusFalha}")
    private String statusFalhaQueue;

    public PedidosService(PedidoRepository repo, @Value("${app.queues.entrada}") String entradaQueue, RabbitTemplate rabbitTemplate) {
        this.repo = repo;
        this.rabbitTemplate = rabbitTemplate;
        this.entradaQueue = entradaQueue;
    }

    public PedidoDTO criarPedido(PedidoDTO dto) {
        // garante id e data
        if (dto.getId() == null) dto.setId(UUID.randomUUID());
        if (dto.getDataCriacao() == null) dto.setDataCriacao(LocalDateTime.now());

        // salva e marca recebido
        Pedido p = toEntity(dto);
        p.setStatus("RECEBIDO");
        repo.save(p);

        // publica objeto pedido completo em json
        rabbitTemplate.convertAndSend(entradaQueue, dto);
        dto.setStatus("ENFILEIRADO");
        repo.updateStatus(dto.getId(), "ENFILEIRADO");

        return dto;
    }

    public String getStatus(UUID id) {
        return repo.getStatus(id);
    }

    // consumer da fila de entrada (processa 1-3s e 20% falha)
    @RabbitListener(queues = "${app.queues.entrada}")
    public void consumirPedido(PedidoDTO dto) {
        UUID id = dto.getId();
        repo.updateStatus(id, "PROCESSANDO");
        try {
            Thread.sleep(3000L);
            if (numberRandom.nextDouble() < 0.2) { // chance de falha
                throw new RuntimeException("Falha simulada de processamento");
            }

            // sucesso
            repo.updateStatus(id, "SUCESSO");
            StatusPedidoDTO status = new StatusPedidoDTO(id, "SUCESSO", LocalDateTime.now(), null);
            rabbitTemplate.convertAndSend(statusSucessoQueue, status);

        } catch (Exception ex) {

            // publica status de falha e rejeita
            repo.updateStatus(id, "FALHA");
            StatusPedidoDTO status = new StatusPedidoDTO(id, "FALHA", LocalDateTime.now(), ex.getMessage());
            rabbitTemplate.convertAndSend(statusFalhaQueue, status);

            throw new AmqpRejectAndDontRequeueException("Erro no processamento: " + ex.getMessage(), ex);
        }
    }

    private Pedido toEntity(PedidoDTO dto) {
        Pedido p = new Pedido();
        p.setId(dto.getId());
        p.setProduto(dto.getProduto());
        p.setQuantidade(dto.getQuantidade());
        p.setDataCriacao(dto.getDataCriacao());
        return p;
    }
}
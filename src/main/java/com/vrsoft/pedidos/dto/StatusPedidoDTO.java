package com.vrsoft.pedidos.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class StatusPedidoDTO {
    private UUID idPedido;
    private String status; // sucesso ou falha
    private LocalDateTime dataProcessamento;
    private String mensagemErro;

    public StatusPedidoDTO() {}
    public StatusPedidoDTO(UUID id, String status, LocalDateTime data, String err) {
        this.idPedido = id; this.status = status; this.dataProcessamento = data; this.mensagemErro = err;
    }

    // getters e setters

    public UUID getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(UUID idPedido) {
        this.idPedido = idPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }
}
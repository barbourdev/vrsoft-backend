package com.vrsoft.pedidos.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


import java.time.LocalDateTime;
import java.util.UUID;

public class PedidoDTO {
    private UUID id;
    @NotBlank(message = "O produto e obrigatorio")
    private String produto;
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private int quantidade;
    private LocalDateTime dataCriacao;
    private String status;


    // getters e setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
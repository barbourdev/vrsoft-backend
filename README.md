# Backend - VR Software

sistema de Pedidos - Backend (Spring Boot + RabbitMQ)

aplicacao **Spring Boot** responsavel por receber pedidos, publicar em uma fila do **RabbitMQ**, processar de forma assincrona e atualizar o status

faz parte do sistema junto com o **frontend Swing**

## Tecnologias utilizadas
- Java 21 / Spring Boot  
- RabbitMQ / Mensageria  
- Maven   

---

## Funcionalidades
- endpoint **POST /api/pedidos** -> cria e enfileira pedidos 
- endpoint **GET /api/pedidos/status/{id}** -> retorna status atual do pedido
- integracao com **RabbitMQ** para processamento assincrono 
- dead Letter Queue (DLQ) configurada para falhas  
- validacoes no DTO (produto obrigatorio, quantidade minima)  
- tratamento de erros com mensagens em pt-br

---

## Estrutura do projeto

  ```
  src/main/java/com/vrsoft/pedidos
   ├── config/RabbitConfig.java # configs do RabbitMQ 
   ├── controller/PedidosController.java # endpoints
   ├── dto/PedidoDTO.java # objeto de tranferencia dos dados do pedido
   ├── dto/StatusPedidoDTO.java # DTO status
   ├── entity/Pedido.java # entidade do pedido
   ├── repository/PedidoRepository.java# repositorio
   ├── service/PedidosService.java # regras de negocio e integracao com RabbitMQ
   └── exception/GlobalExceptionHandler.java # tratamento dos erros
  ```
---

## Como rodar o projeto localmente

1. clone o repositorio:
   git clone https://github.com/barbourdev/vrsoft-backend.git <br>
   cd vrsoft-backend

2. rode com Maven:
   mvn spring-boot:run

3. a API estara em:
   http://localhost:8080

---

## endpoints
- GET /api/pedidos/status/{ID_PRODUTO} -> buscar um pedido pelo ID
- POST /api/pedidos -> cria um pedido por um body

```
  {
    "produto": "Lapis",
    "quantidade": 5
  }
```

## fila principal e DLQ
app.queues.entrada=pedidos.entrada.${app.user}<br>
app.queues.entradaDlq=pedidos.entrada.${app.user}.dlq<br>
app.queues.statusSucesso=pedidos.status.sucesso.${app.user}<br>
app.queues.statusFalha=pedidos.status.falha.${app.user}<br>

---

## Autor
**Felipe Barbour**  
[LinkedIn](https://linkedin.com/in/felipebarbour) | [GitHub](https://github.com/barbourdev)

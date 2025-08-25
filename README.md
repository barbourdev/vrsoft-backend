# Backend - VR Software

## Tecnologias utilizadas
- Java 21 / Spring Boot  
- RabbitMQ / Mensageria  
- Maven   

---

## Estrutura do projeto
- src/
- main/java
- controller   -> Endpoints REST
- dto          -> DTOs
- entity       -> Classe do projeto
- exception    -> Tratamento de erros
- service      -> Regras de negocio
- repository   -> Acesso a memoria
- config       -> Configurações
- /resources/
- application.properties
- /test/
- PedidosService.java -> Teste de insercao na fila do rabbit
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

## Endpoints
- GET /api/pedidos/status/{ID_PRODUTO} -> buscar um pedido pelo ID
- POST /api/pedidos -> cria um pedido por um body

```
  {
    "produto": "Lapis",
    "quantidade": 5
  }
```

---

## Autor
**Felipe Barbour**  
[LinkedIn](https://linkedin.com/in/felipebarbour) | [GitHub](https://github.com/barbourdev)

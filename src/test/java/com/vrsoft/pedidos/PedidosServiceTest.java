package com.vrsoft.pedidos;

import com.vrsoft.pedidos.dto.PedidoDTO;
import com.vrsoft.pedidos.repository.PedidoRepository;
import com.vrsoft.pedidos.service.PedidosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PedidosServiceTest {

	private RabbitTemplate rabbitTemplate;
	private PedidoRepository repo;
	private PedidosService service;

	@BeforeEach
	void setup() {
		rabbitTemplate = mock(RabbitTemplate.class);
		repo = mock(PedidoRepository.class);

		service = new PedidosService(repo, "pedidos.entrada.teste", rabbitTemplate);
	}

	@Test
	void publicarPedidoNaFila() {

		System.out.println("iniciando teste: publicarPedidoNaFila");

		PedidoDTO dto = new PedidoDTO();
		dto.setProduto("Notebook");
		dto.setQuantidade(2);

		System.out.println("criando o pedido: " + dto.getProduto() + " x" + dto.getQuantidade());

		PedidoDTO criado = service.criarPedido(dto);

		ArgumentCaptor<PedidoDTO> captor = ArgumentCaptor.forClass(PedidoDTO.class);
		verify(rabbitTemplate, times(1))
				.convertAndSend(eq("pedidos.entrada.teste"), captor.capture());

		PedidoDTO enviado = captor.getValue();

		System.out.println("pedido enviado para fila: " + enviado.getId());
		System.out.println("status: " + enviado.getStatus());

		assertThat(enviado.getId()).isNotNull();
		assertThat(enviado.getProduto()).isEqualTo("Notebook");
		assertThat(enviado.getQuantidade()).isEqualTo(2);
		assertThat(enviado.getStatus()).isEqualTo("ENFILEIRADO");

		System.out.println("teste finalizado com sucesso!");
	}
}


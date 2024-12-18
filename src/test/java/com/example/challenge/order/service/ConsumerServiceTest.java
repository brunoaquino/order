package com.example.challenge.order.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.challenge.order.dto.PedidoDto;
import com.example.challenge.order.dto.ProdutoDto;
import com.example.challenge.order.service.impl.ConsumerServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class ConsumerServiceTest {
	@Mock
	private ObjectMapper objectMapper;

	@Mock
	private PedidoService pedidoService;

	@InjectMocks
	private ConsumerServiceImp consumerService;

	private String message;
	private PedidoDto pedidoDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		message = "{\"id\":1, \"produtos\":[{\"id\":1, \"nome\":\"Produto A\", \"preco\":100.0}]}";
		pedidoDto = new PedidoDto();
		pedidoDto.setId(1L);
		pedidoDto.setProdutos(List.of(new ProdutoDto(1L, "Produto A", 100.0)));
	}

	@Test
	void testProcessamentoPedidoQueueListen_Sucesso() throws JsonProcessingException {
		when(objectMapper.readValue(message, PedidoDto.class)).thenReturn(pedidoDto);

		consumerService.processamentoPedidoQueueListen(message);

		verify(pedidoService).calcularValorPedido(pedidoDto);
	}

	@Test
	void testProcessamentoPedidoQueueListen_JsonProcessingException() throws JsonProcessingException {
		when(objectMapper.readValue(message, PedidoDto.class)).thenThrow(JsonProcessingException.class);

		consumerService.processamentoPedidoQueueListen(message);

		verify(pedidoService, never()).calcularValorPedido(any());
	}

	@Test
	void testProcessamentoPedidoQueueListen_ExcecaoAoProcessarPedido() throws JsonProcessingException {
		when(objectMapper.readValue(message, PedidoDto.class)).thenReturn(pedidoDto);

		consumerService.processamentoPedidoQueueListen(message);

		verify(pedidoService).calcularValorPedido(pedidoDto);
	}

	@Test
	void testEntradaPedidoQueueListen_Sucesso() throws JsonProcessingException {
		when(objectMapper.readValue(message, PedidoDto.class)).thenReturn(pedidoDto);

		consumerService.entradaPedidoQueueListen(message);

		verify(pedidoService).receberPedidoDeProdutoExternoA(pedidoDto);
	}

	@Test
	void testEntradaPedidoQueueListen_JsonProcessingException() throws JsonProcessingException {
		when(objectMapper.readValue(message, PedidoDto.class)).thenThrow(JsonProcessingException.class);

		consumerService.entradaPedidoQueueListen(message);

		verify(pedidoService, never()).receberPedidoDeProdutoExternoA(any());
	}

}
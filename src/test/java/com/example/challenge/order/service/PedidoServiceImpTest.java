package com.example.challenge.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.challenge.order.dto.PedidoDto;
import com.example.challenge.order.dto.ProdutoDto;
import com.example.challenge.order.model.Pedido;
import com.example.challenge.order.repository.PedidoRepository;
import com.example.challenge.order.service.impl.PedidoServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;

@SpringBootTest
public class PedidoServiceImpTest {

	@Mock
	private PedidoRepository pedidoRepository;

	@Mock
	private ProducerService producerService;

	@Mock
	private ProdutoService produtoService;

	@InjectMocks
	private PedidoServiceImp pedidoService;

	private PedidoDto pedidoDto;
	private Pedido pedido;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		pedidoDto = new PedidoDto();
		pedidoDto.setId(1L);
		pedidoDto.setProdutos(List.of(new ProdutoDto(1L, "Produto A", 100.0), new ProdutoDto(2L, "Produto B", 200.0)));

		pedido = new Pedido();
		pedido.setId(1L);
		pedido.setStatus("Processado");
	}

	@Test
	void testCalcularValorPedido() throws JsonProcessingException {
		pedidoService.calcularValorPedido(pedidoDto);

		assertEquals(300.0, pedidoDto.getTotal());

		assertEquals("Processado", pedidoDto.getStatus());

		verify(pedidoRepository).save(any(Pedido.class));

		verify(producerService).sendMessageFilaPedidoCalculado(anyString());
	}

	@Test
	void testListar() {
		when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

		List<PedidoDto> pedidos = pedidoService.listar();

		assertNotNull(pedidos);
		assertEquals(1, pedidos.size());
		assertEquals(pedidoDto.getId(), pedidos.get(0).getId());

		verify(pedidoRepository).findAll();
	}

	@Test
	void testReceberPedidoDeProdutoExternoA_ComPedidoValido() throws JsonProcessingException {
		when(pedidoRepository.findById(pedidoDto.getId())).thenReturn(Optional.empty());
		when(produtoService.salvarProduto(any(ProdutoDto.class))).thenReturn(new ProdutoDto());

		pedidoService.receberPedidoDeProdutoExternoA(pedidoDto);

		verify(pedidoRepository).save(any(Pedido.class));

		verify(producerService).sendMessageProcessamento(anyString());
	}

	@Test
	void testReceberPedidoDeProdutoExternoA_ProdutoInvalido() throws JsonProcessingException {
		when(pedidoRepository.findById(pedidoDto.getId())).thenReturn(Optional.of(pedido));

		pedidoService.receberPedidoDeProdutoExternoA(pedidoDto);

		verify(pedidoRepository, never()).save(any(Pedido.class));

		verify(producerService).sendMessageErroRecebimento(anyString());
	}

	@Test
	void testValidarSeProdutoValido_ComProdutoVazio() throws JsonProcessingException {
		pedidoDto.setProdutos(List.of());

		boolean resultado = pedidoService.validarSeProdutoValido(pedidoDto);

		assertFalse(resultado);

		verify(producerService).sendMessageErroRecebimento(anyString());
	}

	@Test
	void testValidarSeProdutoValido_ComPedidoExistente() throws JsonProcessingException {
		when(pedidoRepository.findById(pedidoDto.getId())).thenReturn(Optional.of(pedido));

		boolean resultado = pedidoService.validarSeProdutoValido(pedidoDto);

		assertFalse(resultado);

		verify(producerService).sendMessageErroRecebimento(anyString());
	}

	@Test
	void testConsultarPedido() {
		when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

		PedidoDto result = pedidoService.consultarPedido(1L);

		assertNotNull(result);
		assertEquals(pedidoDto.getId(), result.getId());
		verify(pedidoRepository).findById(1L);
	}

	@Test
	void testConsultarPedido_QuandoPedidoNaoEncontrado() {
		when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> pedidoService.consultarPedido(1L));
	}
}

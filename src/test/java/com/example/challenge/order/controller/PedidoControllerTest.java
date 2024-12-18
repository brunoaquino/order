package com.example.challenge.order.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.challenge.order.dto.PedidoDto;
import com.example.challenge.order.dto.ProdutoDto;
import com.example.challenge.order.service.PedidoService;

@SpringBootTest
@AutoConfigureMockMvc
public class PedidoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PedidoService pedidoService;

	@InjectMocks
	private PedidoController pedidoController;

	private PedidoDto pedidoDto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		pedidoDto = new PedidoDto();
		pedidoDto.setId(1L);
		pedidoDto.setProdutos(List.of(new ProdutoDto(1L, "Produto A", 100.0), new ProdutoDto(2L, "Produto B", 200.0)));
	}

	@Test
	void testConsultarPedido() throws Exception {
		// Configuração do mock para simular o comportamento do PedidoService
		when(pedidoService.consultarPedido(1L)).thenReturn(pedidoDto);

		// Simula uma requisição GET para o endpoint "/pedido/{id}"
		mockMvc.perform(get("/pedido/{id}", 1L)).andExpect(status().isOk()) // Verifica se o status é 200 OK
				.andExpect(jsonPath("$.id").value(1L)); // Verifica se o ID retornado é 1

		// Verifica se o método consultarPedido foi invocado com o ID correto
		verify(pedidoService).consultarPedido(1L);
	}
}
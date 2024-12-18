package com.example.challenge.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.challenge.order.dto.ProdutoDto;
import com.example.challenge.order.model.Produto;
import com.example.challenge.order.repository.ProdutoRepository;
import com.example.challenge.order.service.impl.ProdutoServiceImp;

public class ProdutoServiceImpTest {

	@Mock
	private ProdutoRepository produtoRepository;

	@InjectMocks
	private ProdutoServiceImp produtoService;

	private ProdutoDto produtoDto;
	private Produto produto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		produtoDto = new ProdutoDto();
		produtoDto.setId(1L);
		produtoDto.setNome("Produto Teste");
		produtoDto.setPreco(100.0);

		produto = new Produto();
		produto.setId(1L);
		produto.setNome("Produto Teste");
		produto.setPreco(100.0);
	}

	@Test
	void testSalvarProduto() {
		when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

		ProdutoDto result = produtoService.salvarProduto(produtoDto);

		assertNotNull(result);
		assertEquals(produtoDto.getId(), result.getId());
		assertEquals(produtoDto.getNome(), result.getNome());
		assertEquals(produtoDto.getPreco(), result.getPreco());

		verify(produtoRepository).save(any(Produto.class));
	}

	@Test
	void testBuscarProduto_ComProdutoExistente() {
		when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

		ProdutoDto result = produtoService.buscarProduto(1L);

		assertNotNull(result);
		assertEquals(produtoDto.getId(), result.getId());
		assertEquals(produtoDto.getNome(), result.getNome());
		assertEquals(produtoDto.getPreco(), result.getPreco());

		verify(produtoRepository).findById(1L);
	}

	@Test
	void testBuscarProduto_ComProdutoNaoExistente() {
		when(produtoRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> {
			produtoService.buscarProduto(1L);
		});
	}

	@Test
	void testListarProdutos() {
		when(produtoRepository.findAll()).thenReturn(List.of(produto));

		List<ProdutoDto> produtos = produtoService.listarProdutos();

		assertNotNull(produtos);
		assertEquals(1, produtos.size());
		assertEquals(produtoDto.getId(), produtos.get(0).getId());
		assertEquals(produtoDto.getNome(), produtos.get(0).getNome());

		verify(produtoRepository).findAll();
	}
}
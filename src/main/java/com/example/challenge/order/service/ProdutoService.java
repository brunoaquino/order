package com.example.challenge.order.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.challenge.order.dto.ProdutoDto;

@Transactional
public interface ProdutoService {

	public ProdutoDto salvarProduto(ProdutoDto produtoADto);

	public ProdutoDto buscarProduto(Long id);

	public List<ProdutoDto> listarProdutos();
}

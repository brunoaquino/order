package com.example.challenge.order.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.challenge.order.dto.ProdutoDto;
import com.example.challenge.order.mapper.ProdutoMapper;
import com.example.challenge.order.repository.ProdutoRepository;
import com.example.challenge.order.service.ProdutoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProdutoServiceImp implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoARepository;
	private ProdutoMapper produtoMapper = ProdutoMapper.INSTANCE;

	public ProdutoDto salvarProduto(ProdutoDto produtoADto) {
		return produtoMapper.toDTO(produtoARepository.save(produtoMapper.toEntity(produtoADto)));
	}

	public ProdutoDto buscarProduto(Long id) {
		return produtoMapper.toDTO(
				produtoARepository.findById(id).orElseThrow(() -> new RuntimeException("Produto A não encontrado")));
	}

	public List<ProdutoDto> listarProdutos() {
		return produtoARepository.findAll().stream().map(produtoMapper.INSTANCE::toDTO).collect(Collectors.toList());
	}

}
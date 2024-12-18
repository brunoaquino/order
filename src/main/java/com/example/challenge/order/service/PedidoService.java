package com.example.challenge.order.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.challenge.order.dto.PedidoDto;
import com.fasterxml.jackson.core.JsonProcessingException;

@Transactional
public interface PedidoService {

	public void receberPedidoDeProdutoExternoA(PedidoDto pedido) throws JsonProcessingException;

	public List<PedidoDto> listar();

	public void calcularValorPedido(PedidoDto pedidoDto) throws JsonProcessingException;

	public PedidoDto consultarPedido(Long id);
}

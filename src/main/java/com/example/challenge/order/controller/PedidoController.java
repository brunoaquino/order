package com.example.challenge.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.challenge.order.dto.PedidoDto;
import com.example.challenge.order.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/pedido")
@AllArgsConstructor
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping("/{id}")
	public PedidoDto consultarPedido(@PathVariable Long id) throws JsonProcessingException {
		return pedidoService.consultarPedido(id);
	}

}

package com.example.challenge.order.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.challenge.order.config.RabbitConfig;
import com.example.challenge.order.dto.PedidoDto;
import com.example.challenge.order.service.ConsumerService;
import com.example.challenge.order.service.PedidoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerServiceImp implements ConsumerService {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private PedidoService service;

	@RabbitListener(queues = RabbitConfig.QUEUE_NAME_PROCESSAMENTO)
	public void processamentoPedidoQueueListen(String message) {
		log.info("Received message da fila de processamento: " + message);
		try {
			service.calcularValorPedido(objectMapper.readValue(message, PedidoDto.class));
		} catch (JsonProcessingException e) {
			log.error("Erro ao processar o pedido: {}", e.getMessage());
			e.printStackTrace();
		}
	}

	@RabbitListener(queues = RabbitConfig.QUEUE_NAME_ENTRADA_PEDIDO)
	public void entradaPedidoQueueListen(String message) {
		log.info("Received message da Fila de entrada de pedido: " + message);
		try {
			service.receberPedidoDeProdutoExternoA(objectMapper.readValue(message, PedidoDto.class));
		} catch (JsonProcessingException e) {
			log.error("Erro ao processar o pedido: {}", e.getMessage());
			e.printStackTrace();
		}
	}

	@RabbitListener(queues = RabbitConfig.QUEUE_NAME_PEDIDO_CALCULADO)
	public void pedidoCalculadoQueueListen(String message) {
		log.info("Received message da Fila de pedido calculado: " + message);
		log.info("---------- Integrado com a aplicação B: " + message);
	}

	@RabbitListener(queues = RabbitConfig.QUEUE_NAME_ERRO_RECEBIMENTO)
	public void erroRecebimentoQueueListen(String message) {
		log.info("Received message da Fila de erro ao receber o pedido: " + message);
	}

}
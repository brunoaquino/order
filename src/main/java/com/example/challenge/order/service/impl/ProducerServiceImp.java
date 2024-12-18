package com.example.challenge.order.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.challenge.order.config.RabbitConfig;
import com.example.challenge.order.service.ProducerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerServiceImp implements ProducerService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public void sendMessageProcessamento(String message) {
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_PROCESSAMENTO, message);
		log.debug("Mensagem enviada: " + message);
	}

	@Override
	public void sendMessageFilaEntradaPedido(String message) {
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_ENTRADA_PEDIDO, message);
		log.debug("Mensagem enviada: " + message);
	}

	@Override
	public void sendMessageFilaPedidoCalculado(String message) {
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_PEDIDO_CALCULADO, message);
		log.debug("Mensagem enviada: " + message);
	}

	@Override
	public void sendMessageErroRecebimento(String message) {
		rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_ERRO_RECEBIMENTO, message);
		log.debug("Mensagem enviada: " + message);
	}

}
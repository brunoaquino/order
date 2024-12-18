package com.example.challenge.order.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProducerService {

	public void sendMessageProcessamento(String message);

	public void sendMessageFilaEntradaPedido(String message);

	public void sendMessageFilaPedidoCalculado(String message);

	public void sendMessageErroRecebimento(String message);

}

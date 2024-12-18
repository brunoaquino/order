package com.example.challenge.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableRabbit
@Slf4j
public class RabbitConfig {

	public static final String EXCHANGE_NAME = "pedidoExchange";

	public static final String QUEUE_NAME_PROCESSAMENTO = "processamentoPedidoQueue";
	public static final String ROUTING_KEY_PROCESSAMENTO = "processamentoPedidoRota";

	public static final String QUEUE_NAME_ENTRADA_PEDIDO = "entradaPedidoQueue";
	public static final String ROUTING_KEY_ENTRADA_PEDIDO = "entradaPedidoRota";

	public static final String QUEUE_NAME_PEDIDO_CALCULADO = "pedidoCalculadoQueue";
	public static final String ROUTING_KEY_PEDIDO_CALCULADO = "pedidoCalculadoRota";

	public static final String QUEUE_NAME_ERRO_RECEBIMENTO = "erroRecebimentoQueue";
	public static final String ROUTING_KEY_ERRO_RECEBIMENTO = "erroRecebimentoRota";

	@Bean
	public TopicExchange exchangePostagem() {
		return new TopicExchange(EXCHANGE_NAME);
	}

	@Bean
	@Qualifier(QUEUE_NAME_PROCESSAMENTO)
	public Queue queueProcessamento() {
		return new Queue(QUEUE_NAME_PROCESSAMENTO, false);
	}

	@Bean
	public Binding bindingProcessamento(@Qualifier(QUEUE_NAME_PROCESSAMENTO) Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_PROCESSAMENTO);
	}

	@Bean
	@Qualifier(QUEUE_NAME_ENTRADA_PEDIDO)
	public Queue queueEntradaPedido() {
		return new Queue(QUEUE_NAME_ENTRADA_PEDIDO, false);
	}

	@Bean
	public Binding bindingEntradaPedido(@Qualifier(QUEUE_NAME_ENTRADA_PEDIDO) Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ENTRADA_PEDIDO);
	}

	@Bean
	@Qualifier(QUEUE_NAME_PEDIDO_CALCULADO)
	public Queue queuePedidoCalculado() {
		return new Queue(QUEUE_NAME_PEDIDO_CALCULADO, false);
	}

	@Bean
	public Binding bindingPedidoCalculado(@Qualifier(QUEUE_NAME_PEDIDO_CALCULADO) Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_PEDIDO_CALCULADO);
	}

	@Bean
	@Qualifier(QUEUE_NAME_ERRO_RECEBIMENTO)
	public Queue queueErroRecebimento() {
		return new Queue(QUEUE_NAME_ERRO_RECEBIMENTO, false);
	}

	@Bean
	public Binding bindingErroRecebimento(@Qualifier(QUEUE_NAME_ERRO_RECEBIMENTO) Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_ERRO_RECEBIMENTO);
	}

}

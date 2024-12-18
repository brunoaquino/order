package com.example.challenge.order.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.challenge.order.config.RabbitConfig;
import com.example.challenge.order.service.impl.ProducerServiceImp;

@SpringBootTest
public class ProducerServiceTest {

	@Mock
	private RabbitTemplate rabbitTemplate;

	@InjectMocks
	private ProducerServiceImp producerService;

	private String message;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		this.message = """
				{
				    "id": 1,
				    "produtos": [
				        {
				            "id": 1,
				            "nome": "produto de teste",
				            "preco": 10.5
				        }
				    ]
				}
				""";
	}

	@Test
	void testSendMessageEnviado() {
		producerService.sendMessageFilaPedidoCalculado(this.message);

		verify(rabbitTemplate).convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_PEDIDO_CALCULADO,
				this.message);
	}

	@Test
	void testSendMessageEmProcessamento() {
		producerService.sendMessageProcessamento(this.message);

		verify(rabbitTemplate).convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ROUTING_KEY_PROCESSAMENTO,
				this.message);
	}
}
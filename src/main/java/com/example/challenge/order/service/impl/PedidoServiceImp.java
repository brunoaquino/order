package com.example.challenge.order.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.challenge.order.dto.ErroIntegracaoDto;
import com.example.challenge.order.dto.PedidoDto;
import com.example.challenge.order.dto.ProdutoDto;
import com.example.challenge.order.mapper.PedidoMapper;
import com.example.challenge.order.model.Pedido;
import com.example.challenge.order.repository.PedidoRepository;
import com.example.challenge.order.service.PedidoService;
import com.example.challenge.order.service.ProducerService;
import com.example.challenge.order.service.ProdutoService;
import com.example.challenge.order.tipo.StatusPedido;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PedidoServiceImp implements PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private ProducerService producerService;

	@Autowired
	private ProdutoService produtoService;

	@Override
	public void calcularValorPedido(PedidoDto pedidoDto) throws JsonProcessingException {
		pedidoDto.setTotal(pedidoDto.getProdutos().stream().map(ProdutoDto::getPreco).reduce(0.0, (a, b) -> a + b));
		pedidoDto.setStatus(StatusPedido.PROCESSADO.getDescricao());
		Pedido pedido = pedidoRepository.save(PedidoMapper.INSTANCE.toEntity(pedidoDto));

		producerService.sendMessageFilaPedidoCalculado(new ObjectMapper().writeValueAsString(pedido));
	}

	@Override
	public List<PedidoDto> listar() {
		return pedidoRepository.findAll().stream().map(p -> PedidoMapper.INSTANCE.toDTO(p))
				.collect(Collectors.toList());

	}

	@Override
	public void receberPedidoDeProdutoExternoA(PedidoDto pedidoDto) throws JsonProcessingException {
		if (validarSeProdutoValido(pedidoDto)) {
			pedidoDto.getProdutos().stream().map(p -> produtoService.salvarProduto(p));

			Pedido pedido = PedidoMapper.INSTANCE.toEntity(pedidoDto);
			pedido.setStatus(StatusPedido.PENDENTE.getDescricao());

			pedidoRepository.save(pedido);

			producerService.sendMessageProcessamento(new ObjectMapper().writeValueAsString(pedido));
		}
	}

	public boolean validarSeProdutoValido(PedidoDto pedidoDto) throws JsonProcessingException {
		if (pedidoDto.getProdutos().isEmpty()) {
			ErroIntegracaoDto erroIntegracaoDto = new ErroIntegracaoDto();
			erroIntegracaoDto.setMsgErro("Deve conter pelo menos um produto.");
			erroIntegracaoDto.setPedidoDto(pedidoDto);
			producerService.sendMessageErroRecebimento(new ObjectMapper().writeValueAsString(erroIntegracaoDto));
			return false;
		}
		if (!pedidoRepository.findById(pedidoDto.getId()).isEmpty()) {
			ErroIntegracaoDto erroIntegracaoDto = new ErroIntegracaoDto();
			erroIntegracaoDto.setMsgErro("Já existe um pedido com esse identificador.");
			erroIntegracaoDto.setPedidoDto(pedidoDto);
			producerService.sendMessageErroRecebimento(new ObjectMapper().writeValueAsString(erroIntegracaoDto));
			return false;
		}
		return true;
	}

	@Override
	public PedidoDto consultarPedido(Long id) {
		return PedidoMapper.INSTANCE
				.toDTO(pedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado.")));
	}

}

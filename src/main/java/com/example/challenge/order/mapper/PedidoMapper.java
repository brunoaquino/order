package com.example.challenge.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.example.challenge.order.dto.PedidoDto;
import com.example.challenge.order.model.Pedido;

@Mapper
@Component
public interface PedidoMapper {

	PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

	// Método para converter DTO para entidade
	Pedido toEntity(PedidoDto dto);

	// Método para converter entidade para DTO (opcional)
	PedidoDto toDTO(Pedido entity);

}

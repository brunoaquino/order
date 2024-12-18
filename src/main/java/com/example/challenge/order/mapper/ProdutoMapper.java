package com.example.challenge.order.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.challenge.order.dto.ProdutoDto;
import com.example.challenge.order.model.Produto;

@Mapper
public interface ProdutoMapper {

	ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

	// Método para converter DTO para entidade
	Produto toEntity(ProdutoDto dto);

	// Método para converter entidade para DTO (opcional)
	ProdutoDto toDTO(Produto entity);

}

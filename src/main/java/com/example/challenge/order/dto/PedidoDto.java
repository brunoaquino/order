package com.example.challenge.order.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto implements Serializable{

	private Long id;
	private String status;
	private List<ProdutoDto> produtos;
	private Double total;

}

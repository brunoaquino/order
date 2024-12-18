package com.example.challenge.order.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErroIntegracaoDto implements Serializable {

	private String msgErro;
	private PedidoDto pedidoDto;
}
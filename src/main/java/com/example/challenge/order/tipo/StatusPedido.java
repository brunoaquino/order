package com.example.challenge.order.tipo;

public enum StatusPedido {
	PENDENTE("Pendente"), PROCESSADO("Processado"), ENVIADO("Enviado");

	private final String descricao;

	StatusPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}

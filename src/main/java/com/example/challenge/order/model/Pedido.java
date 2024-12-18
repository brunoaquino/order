package com.example.challenge.order.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido implements Serializable{

	@Id
	private Long id;

	@NotNull(message = "Status não pode ser nulo.")
	@Size(min = 3, max = 50, message = "Status deve ter entre 3 e 50 caracteres.")
	private String status;

	private Double total;

	@NotNull(message = "Deve conter pelo menos um produto.")
	private List<Produto> produtos;

}

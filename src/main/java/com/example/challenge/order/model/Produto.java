package com.example.challenge.order.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto implements Serializable{

	@Id
	private Long id;
	private String nome;
	private double preco;

}
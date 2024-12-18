package com.example.challenge.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.challenge.order.model.Pedido;

public interface PedidoRepository extends MongoRepository<Pedido, Long> {
}
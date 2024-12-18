package com.example.challenge.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.challenge.order.model.Produto;

public interface ProdutoRepository extends MongoRepository<Produto, Long> {
}
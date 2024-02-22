package com.joey.ProductServer.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.joey.ProductServer.domain.Produto;
import com.joey.ProductServer.domain.Produto.Status;

@Repository
public interface IProdutoRepository extends MongoRepository<Produto, String>{

	// aq vc pode criar metodos adicionais
	
	Optional<Produto> findByCode(String codigo);
	
	Page<Produto> findAllByStatus(Pageable pageable, Status status);
}

package com.joey.ClientServer.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.joey.ClientServer.domain.Cliente;

@Repository
public interface IclientRepository extends MongoRepository<Cliente, String>{
	
	Optional<Cliente> findByCpf(Long cpf);

}

package repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import domain.Cliente;

@Repository
public interface IclientRepository extends MongoRepository<Cliente, String>{
	
	Optional<Cliente> findByCPF(Long cpf);

}

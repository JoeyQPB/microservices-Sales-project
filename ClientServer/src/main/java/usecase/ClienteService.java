package usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import domain.Cliente;
import exceptions.EntityNotFoundException;
import jakarta.validation.Valid;
import repository.IclientRepository;

@Service
public class ClienteService {
	
	private IclientRepository repository;
	
	@Autowired
	public ClienteService(IclientRepository repository) {
		this.repository = repository;
	}
	
	public Page<Cliente> getPage(Pageable pageable) {
		return this.repository.findAll(pageable);
	}
	
	public Cliente findById(String id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Cliente.class, "Id", id));
	}

	public Boolean isRegistered(String id) {
		Optional<Cliente> c = this.repository.findById(id);
		return c.isPresent() ? true : false;
	}
	
	public Cliente findByCPF(Long cpf) {
		return this.repository.findByCPF(cpf)
				.orElseThrow(() -> new EntityNotFoundException(Cliente.class, "cpf", String.valueOf(cpf)));
	}
	
	
	public Cliente create(@Valid Cliente cliente) {
		return this.repository.insert(cliente);
	}
	
	public Cliente update(@Valid Cliente cliente) {
		return this.repository.save(cliente);
	}
	
	public void remove(String id) {
		this.repository.deleteById(id);
	}
}

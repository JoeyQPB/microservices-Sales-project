package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import domain.Cliente;
import exceptions.ControllerException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import usecase.ClienteService;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

	private ClienteService clienteService;
	
	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}
	
	public ResponseEntity<Page<Cliente>> getPage(Pageable page) {
		return ResponseEntity.ok(clienteService.getPage(page));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar cliente pelo seu ID")
	public ResponseEntity<Cliente> getById (@PathVariable(value = "id", required = true) String id) {
		try {
			return ResponseEntity.ok(clienteService.findById(id));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ControllerException(e);
		}
	}
	
	@GetMapping(value = "/teste")
	public String teste () {
		return "ok, certo!"; 
	}
	
	
	@GetMapping(value = "cpf/{cpf}")
	@Operation(summary = "Buscar cliente pelo seu CPF")
	public ResponseEntity<Cliente> getByCpf (@PathVariable(value = "cpf", required = true) Long cpf) {
		try {
			return ResponseEntity.ok(clienteService.findByCPF(cpf));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ControllerException(e);
		}
	}
	
	@GetMapping(value = "isCadastrado/{id}")
	public ResponseEntity<Boolean> isCadastrado (@PathVariable(value = "id", required = true) String id) {
		try {
			return ResponseEntity.ok(this.clienteService.isRegistered(id));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ControllerException(e);
		}
	}
	
	@PostMapping
	@Operation(summary = "Cadastra um novo cliente")
	public ResponseEntity<Cliente> create (@RequestBody @Valid Cliente c) {
		try {
			return ResponseEntity.ok(this.clienteService.create(c));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ControllerException(e);
		}
	}
	
	@PutMapping
	@Operation(summary = "Atualiza um cliente")
	public ResponseEntity<Cliente> updatde(@RequestBody @Valid Cliente c) {
		try {
			return ResponseEntity.ok(this.clienteService.update(c));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ControllerException(e);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Remove um cliente pelo seu identificador único")
	public ResponseEntity<String> remove(@PathVariable(value = "id") String id) {
		try {
			this.clienteService.remove(id);
			return ResponseEntity.ok("Removido com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ControllerException(e);
		}
	}
}

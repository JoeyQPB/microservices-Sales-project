package com.joey.ClientServer.controller;

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

import com.joey.ClientServer.domain.Cliente;
import com.joey.ClientServer.exceptions.ControllerException;
import com.joey.ClientServer.usecase.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

	private ClienteService clienteService;

	@Autowired
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@GetMapping
	@Operation(summary = "Buscar todos os clientes reportando um Page. A objeto com um array de objetos Clientes e um pageable")
	public ResponseEntity<Page<Cliente>> getPage(Pageable page) {
		return ResponseEntity.ok(clienteService.getPage(page));
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar cliente pelo seu ID")
	public ResponseEntity<Cliente> getById(@PathVariable(value = "id", required = true) String id) {
		return ResponseEntity.ok(clienteService.findById(id));
	}

	@GetMapping(value = "cpf/{cpf}")
	@Operation(summary = "Buscar cliente pelo seu CPF")
	public ResponseEntity<Cliente> getByCpf(@PathVariable(value = "cpf", required = true) Long cpf) {
		return ResponseEntity.ok(clienteService.findByCPF(cpf));
	}

	@GetMapping(value = "isCadastrado/{id}")
	@Operation(summary = "Verificar o cadastro de um cliente")
	public ResponseEntity<Boolean> isCadastrado(@PathVariable(value = "id", required = true) String id) {
		return ResponseEntity.ok(this.clienteService.isRegistered(id));
	}

	@PostMapping
	@Operation(summary = "Cadastra um novo cliente")
	public ResponseEntity<Cliente> create(@RequestBody @Valid Cliente c) {
		return ResponseEntity.ok(this.clienteService.create(c));
	}

	@PutMapping
	@Operation(summary = "Atualiza um cliente")
	public ResponseEntity<Cliente> updatde(@RequestBody @Valid Cliente c) {
		return ResponseEntity.ok(this.clienteService.update(c));
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Remove um cliente pelo seu identificador único")
	public ResponseEntity<String> remove(@PathVariable(value = "id") String id) {
		this.clienteService.remove(id);
		return ResponseEntity.ok("Removido com sucesso");
	}
}

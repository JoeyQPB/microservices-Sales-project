package com.joey.ProductServer.controller;

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

import com.joey.ProductServer.domain.Produto;
import com.joey.ProductServer.domain.Produto.Status;
import com.joey.ProductServer.usecase.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoController {
	
	private ProdutoService service;
	
	@Autowired
	public ProdutoController(ProdutoService service) {
		this.service = service;
	}
	
	@PostMapping
	@Operation(summary = "Cadastrar um produto")
	public ResponseEntity<Produto> createProduto(@RequestBody @Valid Produto produto) {
		return ResponseEntity.ok(this.service.create(produto));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar um produto pelo seu ID") 
	public ResponseEntity<Produto> getById(@PathVariable(value = "id", required = true) String id) {
		return ResponseEntity.ok(this.service.getById(id));
	}
	
	@GetMapping
	@Operation(summary = "Busca uma lista paginada de produtos")
	public ResponseEntity<Page<Produto>> buscar(Pageable pageable) {
		return ResponseEntity.ok(this.service.getAll(pageable));
	}
	
	@GetMapping(value = "/status/{status}")
	@Operation(summary = "Busca uma lista paginada de produtos por status")
	public ResponseEntity<Page<Produto>> buscarPorStatus(Pageable pageable, 
			@PathVariable(value = "status", required = true) Status status) {
		return ResponseEntity.ok(this.service.getByStatus(pageable, status));
	}
	
	@GetMapping(value = "/code/{code}")
	@Operation(summary = "Busca um produto pelo codigo")
	public ResponseEntity<Produto> buscarPorCodigo(@PathVariable(value = "code", required = true) String code) {
		return ResponseEntity.ok(this.service.getByCode(code));
	}

	@PutMapping
	@Operation(summary = "Atualiza um produto")
	public ResponseEntity<Produto> atualizar(@RequestBody @Valid Produto produto) {
		return ResponseEntity.ok(this.service.update(produto));
	}	
	
	@PutMapping(value = "/active/{id}")
	@Operation(summary = "Ativar um produto")
	public ResponseEntity<String> turnActive(@PathVariable(value = "id", required = true) String id) {
		this.service.turnActive(id);
		return ResponseEntity.ok("Product activated");
	}
	
	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Desativa um produto pelo seu identificador único")
	public ResponseEntity<String> turnInactive(@PathVariable(value = "id") String id) {
		this.service.turnInactive(id);
		return ResponseEntity.ok("Product deactivated");
	}
}

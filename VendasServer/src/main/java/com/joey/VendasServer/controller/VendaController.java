package com.joey.VendasServer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joey.VendasServer.domain.Venda;
import com.joey.VendasServer.recordsDTO.VendaDTO;
import com.joey.VendasServer.usecase.VendasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/venda")
public class VendaController {
	
	private VendasService service;
	
	@Autowired
	public VendaController(VendasService service) {
		this.service = service;
	}
	
	@GetMapping
	@Operation(summary = "Lista as vendas cadastradas")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Retorna a lista de clientes"),
		    @ApiResponse(responseCode = "400", description = "Requisição malformada ou erro de sintaxe", 
		    		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "BAD_REQUEST"))),
		    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
		    		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "INTERNAL_SERVER_ERROR"))),
		})
	public ResponseEntity<Page<Venda>> buscar(Pageable pageable) {
		return ResponseEntity.ok(service.getAll(pageable));
	}
	
	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar venda por Id")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Retorna a venda"),
		    @ApiResponse(responseCode = "400", description = "Requisição malformada ou erro de sintaxe", 
		    		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "BAD_REQUEST"))),
		    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
		    		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "INTERNAL_SERVER_ERROR"))),
		})
	public ResponseEntity<Venda> buscarPorID(@PathVariable(value = "id", required = true) String id) {
		return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping(value = "code/{code}")
	@Operation(summary = "Buscar venda por Codigo")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Retorna a venda"),
		    @ApiResponse(responseCode = "400", description = "Requisição malformada ou erro de sintaxe", 
		    		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "BAD_REQUEST"))),
		    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção",
		    		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "INTERNAL_SERVER_ERROR"))),
		})
	public ResponseEntity<Venda> buscar(@PathVariable(value = "code", required = true) String code) {
		return ResponseEntity.ok(service.getByCode(code));
	}
	
	@PostMapping
	@Operation(summary = "Iniciar uma venda")
	public ResponseEntity<Venda> cadastrar(@RequestBody @Valid VendaDTO venda) {
		return ResponseEntity.ok(service.create(venda));
	}
	
	@PutMapping("/{id}/{codigoProduto}/{quantidade}/addProduto")
	public ResponseEntity<Venda> adicionarProduto(
			@PathVariable(name = "id", required = true) String id,
			@PathVariable(name = "codigoProduto", required = true) String codigoProduto,
			@PathVariable(name = "quantidade", required = true) Integer quantidade) {
		return ResponseEntity.ok(service.adicionarProduto(id, codigoProduto, quantidade));
	}

	@PutMapping("/{id}/{codigoProduto}/{quantidade}/removeProduto")
	public ResponseEntity<Venda> removerProduto(
			@PathVariable(name = "id", required = true) String id,
			@PathVariable(name = "codigoProduto", required = true) String codigoProduto,
			@PathVariable(name = "quantidade", required = true) Integer quantidade) {
		return ResponseEntity.ok(service.removerProduto(id, codigoProduto, quantidade));
	}
	
	@PutMapping(value = "finish/{id}")
	@Operation(summary = "Finalizar venda")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Atualiza o status da venda para CONCLUIDO(2)"),
			@ApiResponse(responseCode = "400", description = "Requisição malformada ou erro de sintaxe", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "BAD_REQUEST"))),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "INTERNAL_SERVER_ERROR"))), })
	public ResponseEntity<Venda> finlaziarVenda(@PathVariable(name = "id", required = true) String id) {
		return ResponseEntity.ok(service.finalizarVenda(id));
	}
	
	@PutMapping(value = "cancel/{id}")
	@Operation(summary = "Buscar venda por Codigo")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Atualiza o status da venda para CANCELADO(3)"),
			@ApiResponse(responseCode = "400", description = "Requisição malformada ou erro de sintaxe", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "BAD_REQUEST"))),
			@ApiResponse(responseCode = "500", description = "Foi gerada uma exceção", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = @ExampleObject(value = "INTERNAL_SERVER_ERROR"))), })
	public ResponseEntity<Venda> cancelarVenda(@PathVariable(name = "id", required = true) String id) {
		return ResponseEntity.ok(service.cancelarVenda(id));
	}
	
	@PutMapping("update/{id}/{statusInt}")
	public ResponseEntity<Venda> adicionarProduto(
			@PathVariable(name = "id", required = true) String id,
			@PathVariable(name = "statusInt", required = true) Integer statusInt) {
		return ResponseEntity.ok(service.update(id, statusInt));
	}

}

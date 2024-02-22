package com.joey.ProductServer.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.joey.ProductServer.domain.Produto;
import com.joey.ProductServer.domain.Produto.Status;
import com.joey.ProductServer.exception.EntityNotFoundException;
import com.joey.ProductServer.repository.IProdutoRepository;

import jakarta.validation.Valid;

@Service
public class ProdutoService {

	private IProdutoRepository repository;
	
	@Autowired
	public ProdutoService(IProdutoRepository repository) {
		this.repository = repository;
	}
	
	public Page<Produto> getAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}
	
	public Page<Produto> getByStatus(Pageable pageable, Status status) {
		return this.repository.findAllByStatus(pageable, status);
	}
	
	public Produto getById(String id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Produto.class, "id", id));
	}

	public Produto getByCode(String codigo) {
		return this.repository.findByCode(codigo)
				.orElseThrow(() -> new EntityNotFoundException(Produto.class, "codigo", codigo));
	}
	
	public Produto create(@Valid Produto produto) {
		produto.setStatus(Status.ATIVO);
		return this.repository.insert(produto);
	}

	public Produto update(@Valid Produto produto) {
		return this.repository.save(produto);
	}

	public void turnInactive(String id) {
		Produto prod = repository.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(Produto.class, "id", id));
		prod.setStatus(Status.INATIVO);
		this.repository.save(prod);
		//this.produtoRepository.deleteById(id);
	}
	
	public void turnActive(String id) {
		Produto prod = repository.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(Produto.class, "id", id));
		prod.setStatus(Status.ATIVO);
		this.repository.save(prod);
	}
	
}

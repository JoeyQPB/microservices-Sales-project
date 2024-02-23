package com.joey.VendasServer.usecase;

import java.time.Instant;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.joey.VendasServer.domain.Produto;
import com.joey.VendasServer.domain.ProdutoQuantidade;
import com.joey.VendasServer.domain.Venda;
import com.joey.VendasServer.domain.enums.Status;
import com.joey.VendasServer.exception.EntityNotFoundException;
import com.joey.VendasServer.recordsDTO.VendaDTO;
import com.joey.VendasServer.repository.IVendaRepository;
import com.joey.VendasServer.services.ClienteService;
import com.joey.VendasServer.services.IProdutoService;

import jakarta.validation.Valid;

@Service
public class VendasService {

	private IVendaRepository repository;
	private IProdutoService produtoService;
	private ClienteService clienteService;
	
	@Autowired
	public VendasService(IVendaRepository repository, IProdutoService produtoService, ClienteService clienteService) {
		this.repository = repository;
		this.produtoService = produtoService;
		this.clienteService = clienteService;
	}
	
	public Page<Venda> getAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	public Venda getByCode(String codigo) {
		return this.repository.findByCodigo(codigo)
				.orElseThrow(() -> new EntityNotFoundException(Venda.class, "codigo", codigo));
	}
	
	public Venda getById(String id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Venda.class, "id", id));
		
	}

	public Venda create(@Valid VendaDTO vendaDTO) {
		Venda venda = convertToDomain(vendaDTO);
		validarCliente(venda.getClienteId());
		venda.recalcularValorTotalVenda();
		return this.repository.insert(venda);
	}
	
	private void validarCliente(String clienteId) {
		Boolean isCadastrado = this.clienteService.isClienteCadastrado(clienteId);
		if (!isCadastrado) {
			new EntityNotFoundException(Venda.class, "clienteId", clienteId);
		}
	}
	
	private Venda convertToDomain(@Valid VendaDTO vendaDTO) {
		Venda venda = new Venda();
		venda.setClienteId(vendaDTO.clienteId());
		venda.setCodigo(vendaDTO.codigo());
		venda.setDataVenda(Instant.now());
		venda.setStatus(Status.getStatusByCodigo(0));
		venda.setValorTotal(0.0);
		venda.setProdutos(new HashSet<ProdutoQuantidade>());
		return venda;
	}
	
	public Venda update(String id, Integer statusCode) {
		Venda venda = getById(id);
		venda.setStatus(Status.getStatusByCodigo(statusCode));
		return this.repository.save(venda);
	}
	
	public Venda finalizarVenda(String id) {
		Venda venda = getById(id);
		venda.validarStatus();
		venda.setStatus(Status.CONCLUIDA);
		return this.repository.save(venda);
	}
	
	public Venda cancelarVenda(String id) {
		Venda venda = getById(id);
		venda.validarStatus();
		venda.setStatus(Status.CANCELADA);
		return this.repository.save(venda);
	}
	
	public Venda adicionarProduto(String id, String codigoProduto, Integer quantidade) {
		Venda venda = getById(id);
		Produto produto = getProduto(codigoProduto);
		venda.validarStatus();
		venda.addProduto(produto, quantidade);
		return this.repository.save(venda);
	}
	
	public Venda removerProduto(String id, String codigoProduto, Integer quantidade) {
		Venda venda = getById(id);
		Produto produto = getProduto(codigoProduto);
		venda.validarStatus();
		venda.removeProduto(produto, quantidade);
		return this.repository.save(venda);
	}
	
	private Produto getProduto(String codigoProduto) {
		Produto prod = produtoService.buscarProduto(codigoProduto);
		if (prod == null) {
			throw new EntityNotFoundException(Produto.class, "codigo", codigoProduto);
		}
		return prod;
	}
}

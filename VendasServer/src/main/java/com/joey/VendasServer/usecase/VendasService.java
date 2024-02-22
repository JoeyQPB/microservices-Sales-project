package com.joey.VendasServer.usecase;

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
	public VendasService(IVendaRepository repository, IProdutoService produtoService) {
		this.repository = repository;
		this.produtoService = produtoService;
	}
	
	public Page<Venda> getAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	public Venda getByCode(String codigo) {
		return this.repository.findByCodigo(codigo)
				.orElseThrow(() -> new EntityNotFoundException(Venda.class, "codigo", codigo));
	}
	
	private Venda getById(String id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Venda.class, "id", id));
		
	}

	public Venda create(@Valid VendaDTO vendaDTO) {
		Venda venda = convertToDomain(vendaDTO, Status.INICIADA);
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
	
	private Venda convertToDomain(@Valid VendaDTO vendaDTO, Status status) {
		Venda venda = new Venda();
		venda.setClienteId(vendaDTO.clienteId());
		venda.setCodigo(vendaDTO.codigo());
		venda.setDataVenda(vendaDTO.dataVenda());
		venda.setStatus(status);
		venda.setValorTotal(0.0);
		venda.setProdutos(new HashSet<ProdutoQuantidade>());
		return venda;
	}
	
	public Venda update(@Valid Venda venda) {
		return this.repository.save(venda);
	}
	
	public Venda finalizar(String id) {
		Venda venda = getById(id);
		venda.validarStatus();
		venda.setStatus(Status.CONCLUIDA);
		return this.repository.save(venda);
	}
	
	public Venda cancelar(String id) {
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

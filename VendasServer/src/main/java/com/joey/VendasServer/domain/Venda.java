package com.joey.VendasServer.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joey.VendasServer.domain.enums.Status;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "venda-service")
@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(name = "Sale", description = "Entidade Sale")
public class Venda {
	
	@Id
	@Schema(name = "Id")
	private String id;

	@NotNull
	@Size(min = 2, max = 10)
	@Indexed(unique = true, background = true)
	@Schema(name = "codigo")
	private String codigo;
	
	@NotNull
	@Schema(name = "clienteId")
	private String clienteId;
	
	@Schema(name = "produtos")
	private Set<ProdutoQuantidade> produtos;
	
	@Schema(name = "valorTotal")
	private Double valorTotal;
	
	@NotNull
	@Schema(name = "dataVenda")
	private Instant dataVenda;
	
	@NotNull
	@Schema(name = "status")
	private Status status;
	
	public Venda() {
		produtos = new HashSet<>();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getClienteId() {
		return clienteId;
	}

	public void setClienteId(String clienteId) {
		this.clienteId = clienteId;
	}

	public Set<ProdutoQuantidade> getProdutos() {
		return produtos;
	}

	public void setProdutos(Set<ProdutoQuantidade> produtos) {
		this.produtos = produtos;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Instant getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Instant dataVenda) {
		this.dataVenda = dataVenda;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void validarStatus() {
		if (this.status == Status.CONCLUIDA || this.status == Status.CANCELADA) {
			throw new UnsupportedOperationException("IMPOSSÍVEL ALTERAR VENDA FINALIZADA OU CANCELADA");
		}
	}
	
	public void recalcularValorTotalVenda() {
		Double valorTotal = 0.0;
		for (ProdutoQuantidade prod : this.produtos) {
			valorTotal += prod.getValorTotal();
		}
		this.valorTotal = valorTotal;
	}
	
	public void removerTodosProdutos() {
		validarStatus();
		produtos.clear();
		valorTotal = 0.0;
	}
	
	public Integer getQuantidadeTotalProdutos() {
		int result = produtos.stream()
		  .reduce(0, (partialCountResult, prod) -> partialCountResult + prod.getQuantidade(), Integer::sum);
		return result;
	}
	
	public void addProduto(Produto produto, Integer quantidade) {
		validarStatus();
		
		Optional<ProdutoQuantidade> prodqtdOP = 
				this.produtos
				.stream()
				.filter(prodqtd -> prodqtd.getProduto().getCode().equals(produto.getCode()))
				.findAny();
		
		if (prodqtdOP.isPresent()) {
			ProdutoQuantidade prodqtd = prodqtdOP.get();
			prodqtd.adicionar(quantidade);
		} else {
			ProdutoQuantidade newProdQtd = new ProdutoQuantidade();
			newProdQtd.setProduto(produto);
			newProdQtd.adicionar(quantidade);
			this.produtos.add(newProdQtd);
		}
		
		recalcularValorTotalVenda();
	}
	
	public void removeProduto(Produto produto, Integer quantidade) {
		validarStatus();
		
		Optional<ProdutoQuantidade> prodqtdOP = 
				this.produtos
				.stream()
				.filter(prodqtd -> prodqtd.getProduto().getCode().equals(produto.getCode()))
				.findAny();
		
		if (prodqtdOP.isPresent()) {
			ProdutoQuantidade prodqtd = prodqtdOP.get();
			if (prodqtd.getQuantidade()>quantidade) {
				prodqtd.remover(quantidade);
			} else this.produtos.remove(prodqtd);
			
			recalcularValorTotalVenda();
		} 
	}
}

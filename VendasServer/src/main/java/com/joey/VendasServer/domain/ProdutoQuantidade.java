package com.joey.VendasServer.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProdutoQuantidade {

	@NotNull
	private Produto produto;
	
	@NotNull
	private Integer quantidade;
	
	private Double valorTotal;
	
	public ProdutoQuantidade() {
		this.quantidade = 0;
		this.valorTotal = 0.0;
	}

	public void adicionar(Integer quantidade) {
		this.quantidade += quantidade;
		this.valorTotal+= this.produto.getPrice()*quantidade;
	}
	
	public void remover(Integer quantidade) {
		this.quantidade -= quantidade;
		Double value = this.produto.getPrice()*quantidade;
		this.valorTotal -= value;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
}

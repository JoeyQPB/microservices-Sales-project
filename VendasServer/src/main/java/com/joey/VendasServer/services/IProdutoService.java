package com.joey.VendasServer.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.joey.VendasServer.domain.Produto;

@FeignClient(name = "produto", url = "${application.produtoService.endpointConsultarProduto}")
public interface IProdutoService {

	@RequestMapping(method = RequestMethod.GET, value = "/code/{codigo}", produces = "application/json", headers = "application/json")
	Produto buscarProduto(@RequestParam("codigo") String codigoProduto);

}
package com.joey.VendasServer.recordsDTO;

import java.time.Instant;
import java.util.Set;

import com.joey.VendasServer.domain.ProdutoQuantidade;
import com.joey.VendasServer.domain.enums.Status;

public record VendaDTO(String codigo, String clienteId, Set<ProdutoQuantidade> produtos, Double valorTotal,  Instant dataVenda, Status status) {

}

package com.joey.ProductServer.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Document(collection = "produto-service")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "Cliente", description = "Entidade Cliente")
public class Produto {
	
	public enum Status {
		ATIVO, INATIVO;
	}
	
	@Id
	@Schema(description = "Identificador único")
	private String id;

	@NotNull
	@Size(min = 2, max = 10)
	@Indexed(unique = true, background = true)
	@Schema(description = "Código único")
	private String code;
	
	@NotNull
	@Size(min = 1, max = 50)
	@Schema(description = "Nome do produto")
	private String name;
	
	@NotNull
	@Size(min = 1, max = 50)
	@Schema(description = "Descrição do produto", minLength = 1, maxLength = 50)
	private String description;
	
	@NotNull
	@Schema(description = "Valor do produto")
	private Double price;
	
	@NotNull
	@Schema(description = "Status do pedido que pode ser: ATIVO ou INATIVO")
	private Status status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description + ", price="
				+ price + ", status=" + status + "]";
	}
}



package com.joey.VendasServer.domain.enums;

public enum Status {
	INICIADA(0),
	CONCLUIDA(1),
	CANCELADA(2);
	
    private final int codigo;

    Status(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static Status getStatusByCodigo(int codigo) {
        for (Status status : Status.values()) {
            if (status.getCodigo() == codigo) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + codigo);
    }

	public static Status getByName(String value) {
		for (Status status : Status.values()) {
            if (status.name().equals(value)) {
                return status;
            }
        }
		return null;
	}
}
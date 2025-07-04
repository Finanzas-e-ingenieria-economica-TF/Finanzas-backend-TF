package com.example.finanzas_back.model;

public enum TipoTasa {
    EFECTIVA("efectiva"),
    NOMINAL("nominal");

    private final String valor;

    TipoTasa(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public static TipoTasa fromString(String valor) {
        for (TipoTasa tipo : TipoTasa.values()) {
            if (tipo.valor.equalsIgnoreCase(valor)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de tasa no válido: " + valor);
    }
}
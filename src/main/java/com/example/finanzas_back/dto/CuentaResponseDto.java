package com.example.finanzas_back.dto;

import com.example.finanzas_back.model.Cuenta;

import java.math.BigDecimal;

public class CuentaResponseDto {

    private Long id;
    private String nombre;
    private String tipo;
    private BigDecimal saldoInicial;
    private Long usuarioId;

    public CuentaResponseDto(Cuenta cuenta) {
        this.id = cuenta.getId();
        this.nombre = cuenta.getNombre();
        this.tipo = cuenta.getTipo();
        this.saldoInicial = cuenta.getSaldoInicial();
        if (cuenta.getUsuario() != null) {
            this.usuarioId = cuenta.getUsuario().getId();
        }
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
package com.example.finanzas_back.dto;

import com.example.finanzas_back.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.example.finanzas_back.dto.CuentaResponseDto; // <-- AÑADE ESTA LÍNEA

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioResponseDto {
    private Long id;
    private String username;
    private String nombre;
    private String email;
    private LocalDateTime fechaCreacion;
    private Boolean activo;

    @JsonIgnore
    private List<CuentaResponseDto> cuentas;

    public UsuarioResponseDto(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.nombre = usuario.getNombre();
        this.email = usuario.getEmail();
        this.fechaCreacion = usuario.getFechaCreacion();
        this.activo = usuario.getActivo();
        if (usuario.getCuentas() != null) {
            this.cuentas = usuario.getCuentas().stream()
                    .map(CuentaResponseDto::new)
                    .collect(Collectors.toList());
        }
    }

    // Getters y Setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<CuentaResponseDto> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaResponseDto> cuentas) {
        this.cuentas = cuentas;
    }
}
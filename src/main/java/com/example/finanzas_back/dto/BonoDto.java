package com.example.finanzas_back.dto;

import com.example.finanzas_back.model.Bono;
import com.example.finanzas_back.model.TipoTasa;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BonoDto {

    private Long id;

    @NotBlank(message = "El nombre del bono es requerido")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @NotNull(message = "El valor nominal es requerido")
    @DecimalMin(value = "0.01", message = "El valor nominal debe ser mayor a 0")
    private BigDecimal valorNominal;

    @NotNull(message = "La tasa de interés es requerida")
    @DecimalMin(value = "0.0", message = "La tasa de interés no puede ser negativa")
    @DecimalMax(value = "1.0", message = "La tasa de interés no puede ser mayor a 1")
    private BigDecimal tasaInteres;

    @NotNull(message = "El tipo de tasa es requerido")
    private TipoTasa tipoTasa;

    private Integer capitalizacion;

    @NotNull(message = "El plazo total es requerido")
    @Min(value = 1, message = "El plazo total debe ser mayor a 0")
    private Integer plazoTotal;

    @NotNull(message = "La frecuencia de pago es requerida")
    @Min(value = 1, message = "La frecuencia de pago debe ser mayor a 0")
    private Integer frecuenciaPago;

    @NotBlank(message = "La moneda es requerida")
    @Size(min = 3, max = 3, message = "La moneda debe tener 3 caracteres")
    private String moneda;

    @Min(value = 0, message = "El plazo de gracia total no puede ser negativo")
    private Integer plazoGraciaTotal = 0;

    @Min(value = 0, message = "El plazo de gracia parcial no puede ser negativo")
    private Integer plazoGraciaParcial = 0;

    @NotNull(message = "La fecha de emisión es requerida")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEmision;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    // Constructors
    public BonoDto() {}

    public BonoDto(Bono bono) {
        this.id = bono.getId();
        this.nombre = bono.getNombre();
        this.valorNominal = bono.getValorNominal();
        this.tasaInteres = bono.getTasaInteres();
        this.tipoTasa = bono.getTipoTasa();
        this.capitalizacion = bono.getCapitalizacion();
        this.plazoTotal = bono.getPlazoTotal();
        this.frecuenciaPago = bono.getFrecuenciaPago();
        this.moneda = bono.getMoneda();
        this.plazoGraciaTotal = bono.getPlazoGraciaTotal();
        this.plazoGraciaParcial = bono.getPlazoGraciaParcial();
        this.fechaEmision = bono.getFechaEmision();
        this.fechaCreacion = bono.getFechaCreacion();
        this.fechaModificacion = bono.getFechaModificacion();
    }

    // Getters and Setters
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

    public BigDecimal getValorNominal() {
        return valorNominal;
    }

    public void setValorNominal(BigDecimal valorNominal) {
        this.valorNominal = valorNominal;
    }

    public BigDecimal getTasaInteres() {
        return tasaInteres;
    }

    public void setTasaInteres(BigDecimal tasaInteres) {
        this.tasaInteres = tasaInteres;
    }

    public TipoTasa getTipoTasa() {
        return tipoTasa;
    }

    public void setTipoTasa(TipoTasa tipoTasa) {
        this.tipoTasa = tipoTasa;
    }

    public Integer getCapitalizacion() {
        return capitalizacion;
    }

    public void setCapitalizacion(Integer capitalizacion) {
        this.capitalizacion = capitalizacion;
    }

    public Integer getPlazoTotal() {
        return plazoTotal;
    }

    public void setPlazoTotal(Integer plazoTotal) {
        this.plazoTotal = plazoTotal;
    }

    public Integer getFrecuenciaPago() {
        return frecuenciaPago;
    }

    public void setFrecuenciaPago(Integer frecuenciaPago) {
        this.frecuenciaPago = frecuenciaPago;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Integer getPlazoGraciaTotal() {
        return plazoGraciaTotal;
    }

    public void setPlazoGraciaTotal(Integer plazoGraciaTotal) {
        this.plazoGraciaTotal = plazoGraciaTotal;
    }

    public Integer getPlazoGraciaParcial() {
        return plazoGraciaParcial;
    }

    public void setPlazoGraciaParcial(Integer plazoGraciaParcial) {
        this.plazoGraciaParcial = plazoGraciaParcial;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
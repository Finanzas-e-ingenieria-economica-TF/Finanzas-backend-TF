package com.example.finanzas_back.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bonos")
public class Bono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "El nombre del bono es requerido")
    @Size(max = 200, message = "El nombre no puede exceder 200 caracteres")
    private String nombre;

    @Column(name = "valor_nominal", nullable = false, precision = 15, scale = 2)
    @NotNull(message = "El valor nominal es requerido")
    @DecimalMin(value = "0.01", message = "El valor nominal debe ser mayor a 0")
    private BigDecimal valorNominal;

    @Column(name = "tasa_interes", nullable = false, precision = 10, scale = 8)
    @NotNull(message = "La tasa de interés es requerida")
    @DecimalMin(value = "0.0", message = "La tasa de interés no puede ser negativa")
    @DecimalMax(value = "1.0", message = "La tasa de interés no puede ser mayor a 1")
    private BigDecimal tasaInteres;

    @Column(name = "tipo_tasa", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El tipo de tasa es requerido")
    private TipoTasa tipoTasa;

    @Column(name = "capitalizacion")
    private Integer capitalizacion;

    @Column(name = "plazo_total", nullable = false)
    @NotNull(message = "El plazo total es requerido")
    @Min(value = 1, message = "El plazo total debe ser mayor a 0")
    private Integer plazoTotal;

    @Column(name = "frecuencia_pago", nullable = false)
    @NotNull(message = "La frecuencia de pago es requerida")
    @Min(value = 1, message = "La frecuencia de pago debe ser mayor a 0")
    private Integer frecuenciaPago;

    @Column(nullable = false, length = 3)
    @NotBlank(message = "La moneda es requerida")
    @Size(min = 3, max = 3, message = "La moneda debe tener 3 caracteres")
    private String moneda;

    @Column(name = "plazo_gracia_total", nullable = false)
    @Min(value = 0, message = "El plazo de gracia total no puede ser negativo")
    private Integer plazoGraciaTotal = 0;

    @Column(name = "plazo_gracia_parcial", nullable = false)
    @Min(value = 0, message = "El plazo de gracia parcial no puede ser negativo")
    private Integer plazoGraciaParcial = 0;

    @Column(name = "fecha_emision", nullable = false)
    @NotNull(message = "La fecha de emisión es requerida")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEmision;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Constructors
    public Bono() {
        this.fechaCreacion = LocalDateTime.now();
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaModificacion = LocalDateTime.now();
    }

    // Validation methods simplificados para evitar problemas con OpenAPI
    public boolean isValidGracePeriods() {
        if (plazoGraciaTotal == null || plazoGraciaParcial == null || plazoTotal == null) {
            return true;
        }
        return (plazoGraciaTotal + plazoGraciaParcial) < plazoTotal;
    }

    public boolean isValidCapitalization() {
        if (tipoTasa == TipoTasa.NOMINAL) {
            return capitalizacion != null && capitalizacion > 0;
        }
        return true;
    }
}

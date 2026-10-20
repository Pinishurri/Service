package com.sensoritoys.ms_pedidos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VentaDashboardDTO {
    
    private Long pedidoId;
    private String nombreUsuario;
    private BigDecimal totalVenta;
    private LocalDateTime fechaRegistro;
    private String estadoVenta;

    // Constructor vacío
    public VentaDashboardDTO() {
    }

    // Constructor con parámetros
    public VentaDashboardDTO(Long pedidoId, String nombreUsuario, BigDecimal totalVenta, LocalDateTime fechaRegistro, String estadoVenta) {
        this.pedidoId = pedidoId;
        this.nombreUsuario = nombreUsuario;
        this.totalVenta = totalVenta;
        this.fechaRegistro = fechaRegistro;
        this.estadoVenta = estadoVenta;
    }

    // Getters y Setters 
    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstadoVenta() {
        return estadoVenta;
    }

    public void setEstadoVenta(String estadoVenta) {
        this.estadoVenta = estadoVenta;
    }
}
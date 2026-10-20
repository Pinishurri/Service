package com.sensoritoys.ms_productos.dto;

public class StockDashboardDTO {
    
    private Long productoId;
    private String nombreProducto;
    private Integer stockDisponible;
    private Boolean alertaStockBajo;

    // Constructor vacío
    public StockDashboardDTO() {
    }

    // Constructor con parámetros
    public StockDashboardDTO(Long productoId, String nombreProducto, Integer stockDisponible, Boolean alertaStockBajo) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.stockDisponible = stockDisponible;
        this.alertaStockBajo = alertaStockBajo;
    }

    // Getters y Setters 
    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getStockDisponible() {
        return stockDisponible;
    }

    public void setStockDisponible(Integer stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public Boolean getAlertaStockBajo() {
        return alertaStockBajo;
    }

    public void setAlertaStockBajo(Boolean alertaStockBajo) {
        this.alertaStockBajo = alertaStockBajo;
    }
}
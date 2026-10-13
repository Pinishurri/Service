package com.sensoritoys.ms_productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sensoritoys.ms_productos.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
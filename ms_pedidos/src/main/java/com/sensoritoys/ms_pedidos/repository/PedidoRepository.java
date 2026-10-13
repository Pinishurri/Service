package com.sensoritoys.ms_pedidos.repository;

import com.sensoritoys.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
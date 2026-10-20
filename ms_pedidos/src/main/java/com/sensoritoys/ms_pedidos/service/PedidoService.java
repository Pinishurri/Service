package com.sensoritoys.ms_pedidos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sensoritoys.ms_pedidos.model.DetallePedido;
import com.sensoritoys.ms_pedidos.model.Pedido;
import com.sensoritoys.ms_pedidos.repository.PedidoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido crearPedido(Pedido pedido) {
        pedido.setFechaCreacion(LocalDateTime.now());
        
        // Cálculo automático del total a partir de los detalles
        BigDecimal totalCalculado = BigDecimal.ZERO;
        if (pedido.getDetalles() != null) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                BigDecimal subtotal = detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad()));
                totalCalculado = totalCalculado.add(subtotal);
            }
        }
        pedido.setTotal(totalCalculado);

        return pedidoRepository.save(pedido);
    }

    
}
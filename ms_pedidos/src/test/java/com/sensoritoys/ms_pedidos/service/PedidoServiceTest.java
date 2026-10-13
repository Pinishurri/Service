package com.sensoritoys.ms_pedidos.service;

import com.sensoritoys.ms_pedidos.model.DetallePedido;
import com.sensoritoys.ms_pedidos.model.Pedido;
import com.sensoritoys.ms_pedidos.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void testCrearPedidoCalculaTotalCorrectamente() {
        DetallePedido detalle = new DetallePedido(null, 1L, 2, BigDecimal.valueOf(50.00));
        Pedido pedido = new Pedido(null, 10L, null, null, List.of(detalle));
        Pedido pedidoGuardado = new Pedido(1L, 10L, null, BigDecimal.valueOf(100.00), List.of(detalle));

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

        Pedido resultado = pedidoService.crearPedido(pedido);

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(100.00), resultado.getTotal());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void testListarPedidos() {
        when(pedidoRepository.findAll()).thenReturn(List.of(new Pedido()));
        List<Pedido> pedidos = pedidoService.listarPedidos();
        assertFalse(pedidos.isEmpty());
    }
}
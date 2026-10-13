package com.sensoritoys.ms_productos.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sensoritoys.ms_productos.model.Producto;
import com.sensoritoys.ms_productos.repository.ProductoRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testGuardarProducto() {
        Producto producto = new Producto(null, "Auto Juguete", "Carro de carreras", BigDecimal.valueOf(15.99), 10, null);
        Producto guardado = new Producto(1L, "Auto Juguete", "Carro de carreras", BigDecimal.valueOf(15.99), 10, null);

        when(productoRepository.save(any(Producto.class))).thenReturn(guardado);

        Producto resultado = productoService.guardarProducto(producto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Auto Juguete", resultado.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testActualizarStockInsuficiente() {
        Producto producto = new Producto(1L, "Muñeca", "Muñeca parlante", BigDecimal.valueOf(20.00), 2, null);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productoService.actualizarStock(1L, 5);
        });

        assertTrue(exception.getMessage().contains("Stock insuficiente"));
    }

    @Test
    void testProductoNoEncontrado() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            productoService.actualizarStock(99L, 1);
        });
    }
}
package com.sensoritoys.ms_pedidos.controller;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensoritoys.ms_pedidos.model.DetallePedido;
import com.sensoritoys.ms_pedidos.model.Pedido;
import com.sensoritoys.ms_pedidos.repository.PedidoRepository;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        pedidoRepository.deleteAll();
    }

    @Test
    void testCrearYListarPedidos() throws Exception {
        DetallePedido detalle = new DetallePedido(null, 2L, 3, BigDecimal.valueOf(10.00));
        Pedido pedido = new Pedido(null, 1L, null, null, List.of(detalle));

        mockMvc.perform(post("/api/v1/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedido)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.total").value(30.00));

        mockMvc.perform(get("/api/v1/pedidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].total").value(30.00));
    }
}
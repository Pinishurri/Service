package com.sensoritoys.ms_usuarios.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensoritoys.ms_usuarios.dto.LoginRequestDTO;
import com.sensoritoys.ms_usuarios.model.EstadoUsuario;
import com.sensoritoys.ms_usuarios.model.NombreRol;
import com.sensoritoys.ms_usuarios.model.Rol;
import com.sensoritoys.ms_usuarios.model.Usuario;
import com.sensoritoys.ms_usuarios.repository.RolRepository;
import com.sensoritoys.ms_usuarios.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UsuarioControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void cleanDb() {
        usuarioRepository.deleteAll();
        rolRepository.deleteAll();
    }

    @Test
    void testCrearYObtenerUsuario() throws Exception {
        Rol rol = rolRepository.save(new Rol(null, NombreRol.CLIENTE));
        Usuario usuario = new Usuario(null, "Ana Lopez", "ana@test.com", "hash123", "Calle 1", "ref-001", EstadoUsuario.ACTIVO, null, rol);

        mockMvc.perform(post("/api/v1/usuarios/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Ana Lopez"));
    }

    @Test
    void testEliminarUsuarioLogicamente() throws Exception {
        Rol rol = rolRepository.save(new Rol(null, NombreRol.CLIENTE));
        Usuario usuario = new Usuario(null, "Pedro Soto", "pedro@test.com", "hash456", "Calle 2", "ref-002", EstadoUsuario.ACTIVO, null, rol);
        Usuario guardado = usuarioRepository.save(usuario);

        mockMvc.perform(delete("/api/v1/usuarios/" + guardado.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/usuarios/" + guardado.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("INACTIVO"));
    }

    @Test
    void testIniciarSesion() throws Exception {
        Rol rol = rolRepository.save(new Rol(null, NombreRol.CLIENTE));
        Usuario usuario = new Usuario(null, "Ana Lopez", "ana@test.com", "hash123", "Calle 1", "ref-001", EstadoUsuario.ACTIVO, null, rol);
        usuarioRepository.save(usuario);

        LoginRequestDTO loginDto = new LoginRequestDTO("ana@test.com", "hash123");

        String token = mockMvc.perform(post("/api/v1/usuarios/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString().replace("\"", "");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}
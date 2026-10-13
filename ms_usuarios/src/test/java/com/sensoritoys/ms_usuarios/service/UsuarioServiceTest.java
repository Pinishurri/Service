package com.sensoritoys.ms_usuarios.service;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sensoritoys.ms_usuarios.config.JwtUtil;
import com.sensoritoys.ms_usuarios.model.EstadoUsuario;
import com.sensoritoys.ms_usuarios.model.Usuario;
import com.sensoritoys.ms_usuarios.repository.RolRepository;
import com.sensoritoys.ms_usuarios.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void testGuardarUsuario() {
        Usuario usuario = new Usuario(null, "Ana Lopez", "ana@test.com", "hash123", "Calle 1", "ref-001", null, null, null);
        Usuario guardado = new Usuario(1L, "Ana Lopez", "ana@test.com", "hash123", "Calle 1", "ref-001", EstadoUsuario.ACTIVO, null, null);
        
        when(usuarioRepository.findByEmail("ana@test.com")).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(guardado);

        Usuario resultado = usuarioService.guardarUsuario(usuario);

        assertNotNull(resultado);
        assertEquals(EstadoUsuario.ACTIVO, resultado.getEstado());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testGuardarUsuarioDuplicado() {
        Usuario usuario = new Usuario(null, "Ana Lopez", "ana@test.com", "hash123", "Calle 1", "ref-001", null, null, null);
        when(usuarioRepository.findByEmail("ana@test.com")).thenReturn(Optional.of(usuario));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.guardarUsuario(usuario));
        assertEquals("El correo electrónico ya está registrado", exception.getMessage());
    }

    @Test
    void testActualizarUsuarioNoExistente() {
        Usuario datosNuevos = new Usuario(null, "Ghost", "ghost@test.com", "hash000", "Sin dir", "ref-ghost", null, null, null);
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> usuarioService.actualizarUsuario(99L, datosNuevos));
        assertTrue(exception.getMessage().contains("No existe el usuario"));
    }

    @Test
    void testDesactivarUsuario() {
        Usuario usuario = new Usuario(1L, "Ana Lopez", "ana@test.com", "hash123", "Calle 1", "ref-001", EstadoUsuario.ACTIVO, null, null);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        usuarioService.desactivarUsuario(1L);
        assertEquals(EstadoUsuario.INACTIVO, usuario.getEstado());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testIniciarSesionExitoso() {
        Usuario usuario = new Usuario(1L, "Ana", "ana@test.com", "hash123", "C1", "R1", EstadoUsuario.ACTIVO, null, null);
        when(usuarioRepository.findByEmail("ana@test.com")).thenReturn(Optional.of(usuario));
        when(jwtUtil.generarToken("ana@test.com")).thenReturn("token-simulado");

        String resultado = usuarioService.iniciarSesion("ana@test.com", "hash123");
        assertEquals("token-simulado", resultado);
    }

    @Test
    void testIniciarSesionContraseniaIncorrecta() {
        Usuario usuario = new Usuario(1L, "Ana", "ana@test.com", "hash123", "C1", "R1", EstadoUsuario.ACTIVO, null, null);
        when(usuarioRepository.findByEmail("ana@test.com")).thenReturn(Optional.of(usuario));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> usuarioService.iniciarSesion("ana@test.com", "malpass"));
        assertEquals("Credenciales inválidas", exception.getMessage());
    }
}
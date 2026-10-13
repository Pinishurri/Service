package com.sensoritoys.ms_usuarios.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import com.sensoritoys.ms_usuarios.config.JwtUtil;
import com.sensoritoys.ms_usuarios.model.EstadoUsuario;
import com.sensoritoys.ms_usuarios.model.Rol;
import com.sensoritoys.ms_usuarios.model.Usuario;
import com.sensoritoys.ms_usuarios.repository.RolRepository;
import com.sensoritoys.ms_usuarios.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El correo electrónico ya está registrado");
        }
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setFechaCreacion(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario con ID: " + id));

        if (!existente.getEmail().equals(usuario.getEmail()) && 
            usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El nuevo correo ya está en uso");
        }

        existente.setNombre(usuario.getNombre());
        existente.setEmail(usuario.getEmail());
        if (usuario.getContraseniaHash() != null && !usuario.getContraseniaHash().isEmpty()) {
            existente.setContraseniaHash(usuario.getContraseniaHash());
        }
        existente.setDireccionEnvio(usuario.getDireccionEnvio());
        existente.setReferenciaPago(usuario.getReferenciaPago());

        return usuarioRepository.save(existente);
    }

    public void desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario"));
        usuario.setEstado(EstadoUsuario.INACTIVO);
        usuarioRepository.save(usuario);
    }

    public Usuario asignarRol(Long idUsuario, Long idRol) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario"));

        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new EntityNotFoundException("No existe el rol"));

        usuario.setRol(rol);
        return usuarioRepository.save(usuario);
    }

    public String iniciarSesion(String email, String contrasenia) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!usuario.getContraseniaHash().equals(contrasenia)) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        if (usuario.getEstado() == EstadoUsuario.INACTIVO) {
            throw new IllegalStateException("Usuario inactivo");
        }

        return jwtUtil.generarToken(email);
    }

    public void cerrarSesion(String token) {
        if (token == null || token.isEmpty() || !jwtUtil.validarToken(token)) {
            throw new IllegalArgumentException("Token inválido");
        }
    }
}
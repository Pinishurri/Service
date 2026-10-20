package com.sensoritoys.ms_usuarios.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String contraseniaHash;
    private String direccionEnvio;
    private String referenciaPago;

    @Enumerated(EnumType.STRING)
    private EstadoUsuario estado;

    private LocalDateTime fechaCreacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol_id")
    private Rol rol;

    public Usuario() {}

    public Usuario(Long id, String nombre, String email, String contraseniaHash, String direccionEnvio, String referenciaPago, EstadoUsuario estado, LocalDateTime fechaCreacion, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseniaHash = contraseniaHash;
        this.direccionEnvio = direccionEnvio;
        this.referenciaPago = referenciaPago;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.rol = rol;
    }

    // getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseniaHash() { return contraseniaHash; }
    public void setContraseniaHash(String contraseniaHash) { this.contraseniaHash = contraseniaHash; }

    public String getDireccionEnvio() { return direccionEnvio; }
    public void setDireccionEnvio(String direccionEnvio) { this.direccionEnvio = direccionEnvio; }

    public String getReferenciaPago() { return referenciaPago; }
    public void setReferenciaPago(String referenciaPago) { this.referenciaPago = referenciaPago; }

    public EstadoUsuario getEstado() { return estado; }
    public void setEstado(EstadoUsuario estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}
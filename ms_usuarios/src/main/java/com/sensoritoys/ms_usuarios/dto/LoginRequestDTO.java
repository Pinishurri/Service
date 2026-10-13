package com.sensoritoys.ms_usuarios.dto;

public class LoginRequestDTO {
    
    private String email;
    private String contrasenia;

    // Constructor vacío
    public LoginRequestDTO() {}

    // Constructor con parámetros
    public LoginRequestDTO(String email, String contrasenia) {
        this.email = email;
        this.contrasenia = contrasenia;
    }

    // Getters y Setters explícitos (Adiós a los errores en el controlador)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
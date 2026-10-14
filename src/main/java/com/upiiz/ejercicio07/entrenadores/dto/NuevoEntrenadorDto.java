package com.upiiz.ejercicio07.entrenadores.dto;

// Recibir datos del cliente
public class NuevoEntrenadorDto {
    private String nombre;
    private String especialidad;
    private String experiencia;
    private String correo;
    
    public NuevoEntrenadorDto(String nombre, String especialidad, String experiencia, String correo) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.experiencia = experiencia;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}

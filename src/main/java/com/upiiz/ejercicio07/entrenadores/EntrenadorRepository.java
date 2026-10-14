package com.upiiz.ejercicio07.entrenadores;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upiiz.ejercicio07.entrenadores.entities.EntrenadorEntity;
import java.util.List;



 
public interface EntrenadorRepository extends JpaRepository<EntrenadorEntity, Long>{
    Optional<EntrenadorEntity> findById(Long id);
    Optional<EntrenadorEntity> findByNombre(String nombre);
    Optional<EntrenadorEntity> findByCorreo(String correo);
}

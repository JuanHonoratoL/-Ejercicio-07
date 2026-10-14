package com.upiiz.ejercicio07.entrenadores;

import java.util.List;

import com.upiiz.ejercicio07.entrenadores.dto.EntrenadorDto;
import com.upiiz.ejercicio07.entrenadores.dto.NuevoEntrenadorDto;

// Capa de servicio
public interface EntrenadorService {

    EntrenadorDto save(NuevoEntrenadorDto nuevoEntrenadorDto);

    List<EntrenadorDto> findAll();

    EntrenadorDto findById(Long id);

    EntrenadorDto update(Long id, NuevoEntrenadorDto updateEntrenadorDto);

    void delete(Long id);


}

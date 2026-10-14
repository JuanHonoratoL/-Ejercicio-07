package com.upiiz.ejercicio07.entrenadores;

import java.util.List;

import org.springframework.stereotype.Service;

import com.upiiz.ejercicio07.entrenadores.dto.EntrenadorDto;
import com.upiiz.ejercicio07.entrenadores.dto.NuevoEntrenadorDto;
import com.upiiz.ejercicio07.entrenadores.entities.EntrenadorEntity;

@Service
public class EntrenadorServiceImpl implements EntrenadorService {
    private EntrenadorRepository entrenadorRepository;

    // Constructor para la inyección de dependencias
    public EntrenadorServiceImpl(EntrenadorRepository entrenadorRepository) {
        this.entrenadorRepository = entrenadorRepository;
    }

    @Override
    public EntrenadorDto save(NuevoEntrenadorDto nuevoEntrenadorDto) {
        entrenadorRepository.findByNombre(nuevoEntrenadorDto.getNombre())
                .ifPresent(e -> {
                    throw new RuntimeException("Entrenador ya registrado");
                });
        entrenadorRepository.findByCorreo(nuevoEntrenadorDto.getCorreo())
                .ifPresent(e -> {
                    throw new RuntimeException("Correo ya registrado");
                });
        EntrenadorEntity entrenadorGuardado = entrenadorRepository.save(toEntity(nuevoEntrenadorDto));
        return toDomain(entrenadorGuardado);
    }

    @Override
    public List<EntrenadorDto> findAll() {
        return entrenadorRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public EntrenadorDto findById(Long id) {
        EntrenadorEntity entrenador = entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        return toDomain(entrenador);
    }

    @Override
    public EntrenadorDto update(Long id, NuevoEntrenadorDto updateEntrenadorDto) {
        EntrenadorEntity entrenadorActualizado = entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrada"));

        entrenadorActualizado.setNombre(updateEntrenadorDto.getNombre());
        entrenadorActualizado.setCorreo(updateEntrenadorDto.getCorreo());
        entrenadorActualizado.setEspecialidad(updateEntrenadorDto.getEspecialidad());
        entrenadorActualizado.setExperiencia(updateEntrenadorDto.getExperiencia());

        return toDomain(entrenadorRepository.save(entrenadorActualizado));
    }

    @Override
    public void delete(Long id) {
        entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrada"));

        entrenadorRepository.deleteById(id);
    }

    private EntrenadorEntity toEntity(NuevoEntrenadorDto nuevoEntrenadorDto) {
        return new EntrenadorEntity(nuevoEntrenadorDto.getNombre(), nuevoEntrenadorDto.getEspecialidad(),
                nuevoEntrenadorDto.getExperiencia(), nuevoEntrenadorDto.getCorreo());
    }

    private EntrenadorDto toDomain(EntrenadorEntity entrenadorEntity) {
        return new EntrenadorDto(entrenadorEntity.getId(), entrenadorEntity.getNombre(),
                entrenadorEntity.getEspecialidad(), entrenadorEntity.getExperiencia(),
                entrenadorEntity.getCorreo());
    }
}

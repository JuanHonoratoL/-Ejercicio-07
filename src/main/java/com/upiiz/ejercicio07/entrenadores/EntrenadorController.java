package com.upiiz.ejercicio07.entrenadores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.DeleteExchange;

import com.upiiz.ejercicio07.entrenadores.dto.EntrenadorDto;
import com.upiiz.ejercicio07.entrenadores.dto.NuevoEntrenadorDto;
import java.util.List;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@RequestMapping("/api/v1/entrenadores")
public class EntrenadorController {
    private EntrenadorServiceImpl entrenadorService;

    EntrenadorController(EntrenadorServiceImpl entrenadorService){
        this.entrenadorService = entrenadorService;
    }

    // 201 Created: se creó un recurso nuevo
    @PostMapping
    public ResponseEntity<EntityModel<EntrenadorDto>> guardarEntrenador(@RequestBody NuevoEntrenadorDto nuevoEntrenadorDto) {
        EntrenadorDto entrenadorGuardado = entrenadorService.save(nuevoEntrenadorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(agregarEnlaces(entrenadorGuardado));
    }

    // 200 OK: devuelve la lista, cada entrenador con sus enlaces
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<EntrenadorDto>>> listarTodos() {
        List<EntityModel<EntrenadorDto>> entrenadores = entrenadorService.findAll().stream()
                .map(this::agregarEnlaces)
                .toList();
        return ResponseEntity.ok(CollectionModel.of(entrenadores,
                linkTo(methodOn(EntrenadorController.class).listarTodos()).withSelfRel()));
    }

    // 200 OK si existe, 404 Not Found si el id no existe
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<EntrenadorDto>> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(agregarEnlaces(entrenadorService.findById(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 200 OK si se actualizó, 404 Not Found si el id no existe
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<EntrenadorDto>> actualizarEntrenador(@PathVariable Long id, @RequestBody NuevoEntrenadorDto nuevoEntrenadorDto) {
        try {
            return ResponseEntity.ok(agregarEnlaces(entrenadorService.update(id, nuevoEntrenadorDto)));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 204 No Content: se eliminó y no hay cuerpo que devolver
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        entrenadorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // HATEOAS: envuelve al entrenador junto con los enlaces a sus acciones (self, update, delete)
    private EntityModel<EntrenadorDto> agregarEnlaces(EntrenadorDto entrenador) {
        Long id = entrenador.getId();
        return EntityModel.of(entrenador,
                linkTo(methodOn(EntrenadorController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(EntrenadorController.class).actualizarEntrenador(id, null)).withRel("update"),
                linkTo(methodOn(EntrenadorController.class).eliminar(id)).withRel("delete"),
                linkTo(methodOn(EntrenadorController.class).listarTodos()).withRel("entrenadores"));
    }
    
}

package com.espe.estudiantes.controllers;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.espe.estudiantes.models.entities.Estudiante;
import com.espe.estudiantes.services.EstudianteService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Estudiante Estudiante, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errores = new HashMap<>();

            result.getFieldErrors().forEach(
                    err -> errores.put(
                            err.getField(), err.getDefaultMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(Estudiante));
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Estudiante> EstudianteOptional = service.findById(id);
        if (EstudianteOptional.isPresent()) {
            return ResponseEntity.ok(EstudianteOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Estudiante Estudiante, @PathVariable Long id) {
        Optional<Estudiante> EstudianteOptional = service.findById(id);

        if (EstudianteOptional.isPresent()) {
            Estudiante EstudianteDB = EstudianteOptional.get();
            EstudianteDB.setNombre(Estudiante.getNombre());
            EstudianteDB.setApellido(Estudiante.getApellido());
            EstudianteDB.setEmail(Estudiante.getEmail());
            EstudianteDB.setFechaNacimiento(Estudiante.getFechaNacimiento());
            EstudianteDB.setTelefono(Estudiante.getTelefono());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(EstudianteDB));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Estudiante> EstudianteOptional = service.findById(id);

        if (EstudianteOptional.isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}

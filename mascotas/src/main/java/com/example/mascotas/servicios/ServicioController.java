package com.example.mascotas.servicios; // Revisa que este sea tu package real

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    /* 1. Obtener todos */
    @GetMapping
    public ResponseEntity<Iterable<Servicio>> findAll() {
        return ResponseEntity.ok(servicioRepository.findAll());
    }

    /* 2. Crear (El que te está fallando en la imagen) */
    @PostMapping
    public ResponseEntity<Servicio> create(@RequestBody Servicio servicio, UriComponentsBuilder uriComponentsBuilder) {
        Servicio servicioGuardado = servicioRepository.save(servicio);
        URI url = uriComponentsBuilder.path("/servicio/{id}").buildAndExpand(servicioGuardado.getIdServicio()).toUri();
        return ResponseEntity.created(url).body(servicioGuardado);
    }

    /* 3. Obtener por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> findById(@PathVariable Long id) {
        return servicioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* 4. Actualizar por ID */
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> update(@PathVariable Long id, @RequestBody Servicio servicio) {
        if (!servicioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        servicio.setIdServicio(id);
        return ResponseEntity.ok(servicioRepository.save(servicio));
    }

    /* 5. Eliminar por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
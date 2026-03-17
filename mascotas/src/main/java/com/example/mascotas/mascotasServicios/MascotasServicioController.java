package com.example.mascotas.mascotasServicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mascota-servicio")
public class MascotasServicioController {

    @Autowired
    private MascotasServicioRepository repository;

    @PostMapping
    public ResponseEntity<MascotasServicio> crear(@RequestBody MascotasServicio ms) {
        return ResponseEntity.ok(repository.save(ms));
    }

    @GetMapping
    public ResponseEntity<Iterable<MascotasServicio>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotasServicio> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotasServicio> actualizar(@PathVariable Long id, @RequestBody MascotasServicio ms) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        ms.setIdMascotasServicio(id);
        return ResponseEntity.ok(repository.save(ms));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
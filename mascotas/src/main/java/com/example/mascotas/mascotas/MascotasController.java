package com.example.mascotas.mascotas;


import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.clientes.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/mascota")
public class MascotasController {

    @Autowired
    private MascotasRepository mascotaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /* Obtener todas las mascotas */
    @GetMapping
    public ResponseEntity<Iterable<Mascotas>> findAll() {
        return ResponseEntity.ok(mascotaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Mascotas> create(@RequestBody Mascotas mascota, UriComponentsBuilder uriComponentsBuilder) {
        // 1. Corregido: 'Optional' con 't' y uso de findById
        Optional<Cliente> clienteOpcional = clienteRepository.findById(mascota.getCliente().getIdCliente());

        // 2. Corregido: Los Optional ne se comparan con null, se usa .isEmpty() o .isPresent()
        if (clienteOpcional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        // 3. Corregido: .get() es un metodo, lleva paréntesis
        mascota.setCliente(clienteOpcional.get());

        // 4. Agregado: Guardar la mascota y retornar 201 Created con la URL del recurso
        Mascotas mascotaGuardada = mascotaRepository.save(mascota);
        URI url = uriComponentsBuilder.path("/mascota/{id}").buildAndExpand(mascotaGuardada.getIdMascota()).toUri();

        return ResponseEntity.created(url).body(mascotaGuardada);
    }

    /* Obtener una mascota por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Mascotas> findById(@PathVariable Long id) {
        Optional<Mascotas> mascotaOpcional = mascotaRepository.findById(id);
        if (mascotaOpcional.isPresent()) {
            return ResponseEntity.ok(mascotaOpcional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /* Actualizar mascota por ID */
    @PutMapping("/{id}")
    public ResponseEntity<Mascotas> update(@PathVariable Long id, @RequestBody Mascotas mascota) {
        Optional<Mascotas> mascotaOpcional = mascotaRepository.findById(id);
        if (mascotaOpcional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }
        mascota.setIdMascota(id);
        return ResponseEntity.ok(mascotaRepository.save(mascota));
    }

    /* Eliminar mascota por ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Mascotas> mascotaOpcional = mascotaRepository.findById(id);
        if (mascotaOpcional.isPresent()) {
            mascotaRepository.delete(mascotaOpcional.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
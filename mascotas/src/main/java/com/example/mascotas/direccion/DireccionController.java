package com.example.mascotas.direccion;



import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.clientes.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/direccion")
public class DireccionController {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /* 1. Obtener todas */
    @GetMapping
    public ResponseEntity<Iterable<Direccion>> findAll() {
        return ResponseEntity.ok(direccionRepository.findAll());
    }

    /* 2. Crear (El que te está fallando) */
    @PostMapping
    public ResponseEntity<Direccion> create(@RequestBody Direccion direccion, UriComponentsBuilder uriComponentsBuilder) {
        Optional<Cliente> clienteOpcional = clienteRepository.findById(direccion.getCliente().getIdCliente());

        if (clienteOpcional.isEmpty()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        direccion.setCliente(clienteOpcional.get());
        Direccion direccionGuardada = direccionRepository.save(direccion);
        URI url = uriComponentsBuilder.path("/direccion/{id}").buildAndExpand(direccionGuardada.getIdDireccion()).toUri();

        return ResponseEntity.created(url).body(direccionGuardada);
    }

    /* 3. Obtener por ID */
    @GetMapping("/{id}")
    public ResponseEntity<Direccion> findById(@PathVariable Long id) {
        return direccionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* 4. Actualizar */
    @PutMapping("/{id}")
    public ResponseEntity<Direccion> update(@PathVariable Long id, @RequestBody Direccion direccion) {
        if (!direccionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        direccion.setIdDireccion(id);
        return ResponseEntity.ok(direccionRepository.save(direccion));
    }

    /* 5. Eliminar */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (direccionRepository.existsById(id)) {
            direccionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
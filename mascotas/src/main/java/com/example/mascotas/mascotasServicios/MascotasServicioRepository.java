package com.example.mascotas.mascotasServicios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotasServicioRepository extends CrudRepository<MascotasServicio, Long> {
}
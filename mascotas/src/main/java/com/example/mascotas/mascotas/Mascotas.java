package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.Cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.mascotas.mascotasServicios.MascotasServicio;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "mascota")
public class Mascotas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMascota;
    @Column(nullable = false, length = 100)
    private String nombre;
    private char sexo;
    @Column(nullable = false, length = 100)
    private String tipo;
    private byte edad;
    private boolean enPeligro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;

    @OneToMany(mappedBy = "mascota")
    private List<MascotasServicio> mascotaServicios;

}

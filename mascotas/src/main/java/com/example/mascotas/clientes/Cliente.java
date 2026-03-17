package com.example.mascotas.clientes;

import com.example.mascotas.direccion.Direccion;
import com.example.mascotas.mascotas.Mascotas;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @Column(nullable = false, length = 50)
    private String nombre;
    @Column(nullable = false, length = 50)
    private String apPaterno;
    @Column(nullable = false, length = 50)
    private String apMaterno;
    @Column(nullable = false, length = 50)
    private String email;


    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Direccion direccion;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Mascotas> mascotas =  new ArrayList<>();

}

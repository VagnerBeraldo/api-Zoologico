package br.com.senac.ado.zoologico.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veterinario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String nome;
    private String crmv;
    private String especialidade;

    @OneToMany(mappedBy = "veterinario")
    private List<ConsultaVeterinaria> consultas;
}

package br.com.senac.ado.zoologico.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaVeterinaria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private LocalDate dataConsulta;
    private String diagnostico;
    private String tratamento;
    private String observacoes;
    private Boolean urgente;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonBackReference("animal-consultas")
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    @JsonBackReference("veterinario-consultas")
    private Veterinario veterinario;
}

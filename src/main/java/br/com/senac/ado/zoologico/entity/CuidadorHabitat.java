package br.com.senac.ado.zoologico.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuidadorHabitat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tratador_id", nullable = false)
    @JsonBackReference("tratador-cuidadores")
    private Tratador tratador;

    @ManyToOne
    @JoinColumn(name = "habitat_id", nullable = false)
    @JsonBackReference("habitat-cuidadores")
    private Habitat habitat;

    private String turno;
}

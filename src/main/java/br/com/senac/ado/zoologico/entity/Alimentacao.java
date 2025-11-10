package br.com.senac.ado.zoologico.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alimentacao {

    @EmbeddedId
    private AlimentacaoId id;

    private String tipoRacao;
    private Double quantidadeKg;
    private LocalDateTime horario;

    @MapsId("animalId")
    @ManyToOne
    @JoinColumn(name = "animal_id")
    @JsonBackReference
    private Animal animal;

    @MapsId("tratadorId")
    @ManyToOne
    @JoinColumn(name = "tratador_id")
    @JsonBackReference
    private Tratador tratador;
}

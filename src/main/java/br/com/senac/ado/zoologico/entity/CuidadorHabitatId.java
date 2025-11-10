package br.com.senac.ado.zoologico.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuidadorHabitatId implements Serializable {

    @Column(name = "tratador_id", columnDefinition = "BINARY(16)")
    private UUID tratadorId;

    @Column(name = "habitat_id", columnDefinition = "BINARY(16)")
    private UUID habitatId;

    private String turno;
}

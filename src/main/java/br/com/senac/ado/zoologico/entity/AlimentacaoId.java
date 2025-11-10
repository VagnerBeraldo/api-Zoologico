package br.com.senac.ado.zoologico.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlimentacaoId implements Serializable {

    private UUID animalId;
    private UUID tratadorId;
    private LocalDate data;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlimentacaoId that)) return false;
        return Objects.equals(animalId, that.animalId) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalId, data);
    }
}

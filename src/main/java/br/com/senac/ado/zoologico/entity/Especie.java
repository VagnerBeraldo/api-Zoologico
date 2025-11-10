package br.com.senac.ado.zoologico.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Especie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    private String nomeComum;
    private String nomeCientifico;
    private String statusConservacao;

    @OneToMany(mappedBy = "especie")
    @JsonManagedReference("especie-animais")
    private Set<Animal> animais;
}

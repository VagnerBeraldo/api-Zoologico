package br.com.senac.ado.zoologico.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoZoologico {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    private String titulo;
    private String descricao;
    private LocalDate data;
    private Integer capacidade;

    @ManyToMany
    @JoinTable(name = "evento_animal",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_id"))
    @JsonManagedReference
    private Set<Animal> animais;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Bilhete> bilhetes;
}

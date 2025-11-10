package br.com.senac.ado.zoologico.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tratador {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    private String nome;
    private String cpf;
    private String telefone;

    @ManyToMany(mappedBy = "tratadores")
    @JsonBackReference("animal-tratadores")
    private Set<Animal> animais;

    @OneToMany(mappedBy = "tratador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<CuidadorHabitat> habitatsResponsaveis;


}

package br.com.senac.ado.zoologico.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;
import java.util.UUID;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Habitat {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
//    private UUID id;
//
//    private String nome;
//    private String tipo;
//    private Double areaM2;
//
//    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference("habitat-animais")
//    private Set<Animal> animais;
//
//    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference("habitat-cuidadores")
//    private Set<CuidadorHabitat> cuidadores;
//}

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Habitat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID id;

    private String nome;
    private String tipo;
    private Double areaM2;

    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("habitat")
    private Set<Animal> animais;

    @OneToMany(mappedBy = "habitat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("habitat")
    private Set<CuidadorHabitat> cuidadores;
}


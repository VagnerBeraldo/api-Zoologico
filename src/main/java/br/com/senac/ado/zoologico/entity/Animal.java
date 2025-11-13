package br.com.senac.ado.zoologico.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Animal {

//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(columnDefinition = "BINARY(16)")
//    private UUID id;
//
//    private String nome;
//    private String sexo;
//    private LocalDate dataNascimento;
//    private String status;
//
//    @ManyToOne
//    @JoinColumn(name = "especie_id")
//    @JsonBackReference("especie-animais")
//    private Especie especie;
//
//    @ManyToOne
//    @JoinColumn(name = "habitat_id")
//    @JsonBackReference("habitat-animais")
//    private Habitat habitat;
//
//    @ManyToMany
//    @JoinTable(name = "animal_tratador",
//            joinColumns = @JoinColumn(name = "animal_id"),
//            inverseJoinColumns = @JoinColumn(name = "tratador_id"))
//    private Set<Tratador> tratadores;
//
//    @OneToMany(mappedBy = "animal")
//    @JsonManagedReference("animal-consultas")
//    private Set<ConsultaVeterinaria> consultas;


    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    public class Animal {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(columnDefinition = "BINARY(16)")
        private UUID id;

        private String nome;
        private String sexo;
        private LocalDate dataNascimento;
        private String status;

        @ManyToOne
        @JoinColumn(name = "especie_id")
        @JsonIgnoreProperties("animais")
        private Especie especie;

        @ManyToOne
        @JoinColumn(name = "habitat_id")
        @JsonIgnoreProperties({"animais", "cuidadores"})
        private Habitat habitat;

        @ManyToMany
        @JoinTable(name = "animal_tratador",
                joinColumns = @JoinColumn(name = "animal_id"),
                inverseJoinColumns = @JoinColumn(name = "tratador_id"))
        @JsonIgnoreProperties("animais")
        private List<Tratador> tratadores;

        @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL, orphanRemoval = true)
        @JsonIgnoreProperties("animal")
        private List<ConsultaVeterinaria> consultas;

    }




package br.com.senac.ado.zoologico.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bilhete {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    private String comprador;
    private LocalDateTime dataCompra;
    private Double valor;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "evento_id")
    private EventoZoologico evento;
}

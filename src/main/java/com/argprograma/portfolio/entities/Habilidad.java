package com.argprograma.portfolio.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "habilidades")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Habilidad {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="porcentaje")
    private Integer porcentaje;

    @Column(name="url_imagen")
    private String url_imagen;

    @Column(name="orden", unique = true)
    private Integer orden;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="persona_id", nullable = false)
    private Persona persona;
}
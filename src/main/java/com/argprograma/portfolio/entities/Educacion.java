package com.argprograma.portfolio.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "educacion")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Educacion {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nivel")
    private String nivel;

    @Column(name="establecimiento")
    private String establecimiento;

    @Column(name="titulo")
    private String titulo;

    @Column(name="orden", unique = true)
    private Integer orden;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    private Persona persona;

}
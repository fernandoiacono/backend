package com.argprograma.portfolio.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table(name="proyectos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Proyecto {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="extension")
    private String extension;

    @Column(name="link")
    private String link;

    @Column(name="orden", unique = true)
    private Integer orden;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="persona_id", nullable = false)
    private Persona persona;
}
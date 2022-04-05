package com.argprograma.portfolio.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "personas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre")
    private String nombre;

    @Column(name="apellido")
    private String apellido;

    @Column(name="domicilio")
    private String domicilio;

    @Column(name="fecha_nac")
    private Date fecha_nac;

    @Column(name="telefono")
    private String telefono;

    @Column(name="correo", unique = true)
    private String correo;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="sobre_mi")
    private String sobre_mi;

    @Column(name="url_foto")
    private String url_foto;

    @Column(name="facebook_link")
    private String facebook_link;

    @Column(name="github_link")
    private String github_link;

    @JsonBackReference
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Educacion> educacion = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExperienciaLaboral> experiencia_laboral = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Habilidad> habilidades = new HashSet<>();

    @JsonBackReference
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Proyecto> proyectos = new HashSet<>();
}

package com.argprograma.portfolio.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "experiencia_laboral")
@Getter @Setter
public class ExperienciaLaboral {

    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre_empresa")
    private String nombre_empresa;
    @Column(name="es_trabajo_actual")
    private Boolean es_trabajo_actual;
    @Column(name="fecha_inicio")
    private Date fecha_inicio;
    @Column(name="fecha_fin")
    private Date fecha_fin;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="orden", unique = true)
    private Integer orden;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="persona_id", nullable = false)
    private Persona persona;

    @ManyToOne
    @JoinColumn(name="tipo_empleo_id", nullable = false)
    private TipoTrabajo tipo_empleo;
}
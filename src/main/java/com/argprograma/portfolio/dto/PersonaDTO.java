package com.argprograma.portfolio.dto;

import com.argprograma.portfolio.entities.Educacion;
import com.argprograma.portfolio.entities.ExperienciaLaboral;
import com.argprograma.portfolio.entities.Habilidad;
import com.argprograma.portfolio.entities.Proyecto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Getter @Setter
public class PersonaDTO {

    @Positive
    private Long id;

    @NotEmpty
    @NotBlank
    private String nombre;

    @NotEmpty
    @NotBlank
    private String apellido;

    //@NotEmpty
    //@NotBlank
    private String domicilio;

    //@NotEmpty
    //@NotBlank
    private Date fecha_nac;

    //@NotEmpty
    //@NotBlank
    @Size(max = 20, min = 10, message = "El telefono debe tener al menos 10 caracteres")
    private String telefono;

    //@NotEmpty
    //@NotBlank
    @Email
    private String correo;

    @NotEmpty
    @NotBlank
    private String descripcion;

    @NotEmpty
    @NotBlank
    private String sobre_mi;

    //@NotEmpty
    //@NotBlank
    private String url_foto;

    private String facebook_link;

    private String github_link;

    //@NotEmpty
    //@NotBlank
    private List<EducacionDTO> educacion;

    //@NotEmpty
    //@NotBlank
    private List<ExperienciaLaboralDTO> experiencia_laboral;

    //@NotEmpty
    //@NotBlank
    private List<HabilidadDTO> habilidades;

    //@NotEmpty
    //@NotBlank
    private List<ProyectoDTO> proyectos;
}
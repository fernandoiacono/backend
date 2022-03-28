package com.argprograma.portfolio.dto;

import com.argprograma.portfolio.entities.TipoTrabajo;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter @Setter
public class ExperienciaLaboralDTO {

    private Long id;

    @NotEmpty
    @NotBlank
    private String nombre_empresa;

    private Boolean es_trabajo_actual;

    @Past
    private Date fecha_inicio;

    private Date fecha_fin;

    @NotEmpty
    @NotBlank
    private String descripcion;

    @Positive
    private Integer orden;

    @NotNull
    private TipoTrabajo tipo_empleo;

}
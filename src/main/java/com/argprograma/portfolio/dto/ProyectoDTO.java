package com.argprograma.portfolio.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter @Setter
public class ProyectoDTO {

    private Long id;

    @NotEmpty
    @NotBlank
    private String nombre;

    @NotEmpty
    @NotBlank
    private String descripcion;

    @NotEmpty
    @NotBlank
    private String url_imagen;

    @NotEmpty
    @NotBlank
    private String link;

    @Positive
    private Integer orden;

}

package com.argprograma.portfolio.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class TipoEmpleoDTO {

    private Long id;

    @NotEmpty
    @NotBlank
    private String nombre_tipo;
}
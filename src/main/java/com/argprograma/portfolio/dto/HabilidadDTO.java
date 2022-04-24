package com.argprograma.portfolio.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter @Setter
public class HabilidadDTO {

    private Long id;

    @NotEmpty
    @NotBlank
    private String nombre;

    @Positive
    @Digits(integer = 3, fraction = 0)
    private Integer porcentaje;

    //@NotEmpty
    //@NotBlank
    private String extension;

    //@Positive
    private Integer orden;

}
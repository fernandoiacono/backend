package com.argprograma.portfolio.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter @Setter
public class EducacionDTO {

    private Long id;

    @NotEmpty
    @NotBlank
    private String nivel;

    @NotEmpty
    @NotBlank
    private String establecimiento;

    @NotEmpty
    @NotBlank
    private String titulo;

    //@Positive
    private Integer orden;

}
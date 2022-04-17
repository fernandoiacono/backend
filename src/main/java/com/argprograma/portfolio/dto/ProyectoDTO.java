package com.argprograma.portfolio.dto;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class ProyectoDTO {

    private Long id;

    @NotEmpty
    @NotBlank
    private String nombre;

    @NotEmpty
    @NotBlank
    private String descripcion;

//    @NotEmpty
//    @NotBlank
    private String file_type;

    @NotEmpty
    @NotBlank
    private String link;

    //@Positive
    private Integer orden;

}
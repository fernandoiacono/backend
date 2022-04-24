package com.argprograma.portfolio.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class RegisterDTO {

    @NotEmpty
    @NotBlank
    private String username;

    @NotEmpty
    @NotBlank
    private String email;

    @NotEmpty
    @NotBlank
    private String password;
}

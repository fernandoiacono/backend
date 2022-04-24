package com.argprograma.portfolio.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JWTResponseDTO {

    private String token;
    private String tokenType = "Bearer";

    public JWTResponseDTO(String token) {
        this.token = token;
    }
}
package com.argprograma.portfolio.dto;

import lombok.Getter;
import lombok.Setter;

public class JWTResponseDTO {
    @Getter @Setter
    private String token;
    @Getter@Setter
    private String tokenType = "Bearer";

    public JWTResponseDTO(String token) {
        this.token = token;
    }
}
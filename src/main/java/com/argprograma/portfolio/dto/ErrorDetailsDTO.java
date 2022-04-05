package com.argprograma.portfolio.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter @Setter
public class ErrorDetailsDTO {

    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetailsDTO(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}

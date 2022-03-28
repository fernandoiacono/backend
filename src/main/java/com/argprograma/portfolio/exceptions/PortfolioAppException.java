package com.argprograma.portfolio.exceptions;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class PortfolioAppException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    @Getter @Setter
    private HttpStatus status;
    @Getter @Setter
    private String msg;

    public PortfolioAppException(HttpStatus status, String msg) {
        super();
        this.status = status;
        this.msg = msg;
    }

    public PortfolioAppException(HttpStatus status, String msg, String msg1) {
        super();
        this.status = status;
        this.msg = msg;
        this.msg = msg1;
    }
}
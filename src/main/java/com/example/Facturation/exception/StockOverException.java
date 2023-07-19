package com.example.Facturation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StockOverException extends RuntimeException {

    public StockOverException(String message) {
        super(message);
    }

}

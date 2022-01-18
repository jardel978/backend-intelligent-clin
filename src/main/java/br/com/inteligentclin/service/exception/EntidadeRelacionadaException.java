package br.com.inteligentclin.service.exception;

import java.io.Serializable;


public class EntidadeRelacionadaException extends Exception implements Serializable {

    private static final Long serialVersionUID = 1L;

    public EntidadeRelacionadaException(String message) {
        super(message);
    }
}

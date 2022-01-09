package br.com.inteligentclin.service.exception;

import java.io.Serializable;

public class DataIntegrityException extends RuntimeException implements Serializable {

    private static final Long serialVersionUID = 1L;

    public DataIntegrityException(String mensagem) {
        super(mensagem);
    }
}

package br.com.inteligentclin.service.exception;

import java.io.Serializable;

public class ConsultaStatusException extends RuntimeException implements Serializable {

    private static final Long serialVersionUID = 1L;

    public ConsultaStatusException(String mensagem) {
        super(mensagem);
    }
}

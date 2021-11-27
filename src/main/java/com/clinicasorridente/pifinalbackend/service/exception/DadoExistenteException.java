package com.clinicasorridente.pifinalbackend.service.exception;

import java.io.Serializable;

public class DadoExistenteException extends RuntimeException implements Serializable {

    private static final Long serialVersionUID = 1L;

    public DadoExistenteException(String mensagem) {
        super(mensagem);
    }
}

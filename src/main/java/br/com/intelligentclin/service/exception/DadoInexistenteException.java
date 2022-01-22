package br.com.intelligentclin.service.exception;

import java.io.Serializable;

public class DadoInexistenteException extends RuntimeException implements Serializable {

    private static final Long serialVersionUID = 1L;

    public DadoInexistenteException(String mensagem) {
        super(mensagem);
    }
}

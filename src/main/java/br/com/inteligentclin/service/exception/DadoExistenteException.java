package br.com.inteligentclin.service.exception;

import java.io.Serializable;

public class DadoExistenteException extends Exception implements Serializable {

    private static final Long serialVersionUID = 1L;

    public DadoExistenteException(String mensagem) {
        super(mensagem);
    }
}

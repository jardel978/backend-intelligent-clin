package br.com.inteligentclin.service.exception;

import java.io.Serializable;

public class ParametroRequeridoException extends Exception implements Serializable {

    private static final Long serialVersionUID = 1L;

    public ParametroRequeridoException(String mensagem) {
        super(mensagem);
    }
}

package br.com.inteligentclin.controller.exception;

import java.io.Serializable;

public class UniqueElementsException extends Exception implements Serializable {

    private static final Long serialVersionUID = 1L;

    public UniqueElementsException(String mensagem) {
        super(mensagem);
    }
}

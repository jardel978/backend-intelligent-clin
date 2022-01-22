package br.com.intelligentclin.service.exception;

import java.io.Serializable;

public class ValidadeProntuarioException extends Exception implements Serializable {

    private static final Long serialVersionUID = 1L;

    public ValidadeProntuarioException(String message) {
        super(message);
    }
}

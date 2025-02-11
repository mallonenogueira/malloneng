package com.malloneng.backend.domain.exception;

public class InvalidKeywordsException extends ApplicationException {
    public InvalidKeywordsException() {
        super("Palavra chave de pesquisa inválida.");
    }
}

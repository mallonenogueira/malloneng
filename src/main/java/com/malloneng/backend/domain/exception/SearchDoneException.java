package com.malloneng.backend.domain.exception;

public class SearchDoneException extends ApplicationException {
    public SearchDoneException() {
        super("Pesquisa já concluída.");
    }
}

package com.malloneng.backend.domain.exception;

public class SearchNotFoundException extends NotFoundException {
    public SearchNotFoundException() {
        super("Pesquisa não encontrada.");
    }
}

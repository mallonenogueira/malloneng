package com.malloneng.backend.application.usecase.dto;

public class CreateSearchInput {
    private final String keyword;

    public CreateSearchInput(
            String keyword
    ) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}

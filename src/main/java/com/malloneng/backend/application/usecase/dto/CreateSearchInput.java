package com.malloneng.backend.application.usecase.dto;

public class CreateSearchInput {
    private String keyword;

    public CreateSearchInput(
            String keyword
    ) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}

package com.malloneng.backend.domain.entity;

public enum SearchStatus {
    ACTIVE("active"),
    DONE("done");

    private final String text;

    SearchStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}

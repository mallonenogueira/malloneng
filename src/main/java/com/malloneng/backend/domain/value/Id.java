package com.malloneng.backend.domain.value;

import java.util.UUID;

public class Id {
    private final String value;

    private Id(String value) {
        this.value = value;
    }

    public static Id create() {
        return new Id(UUID.randomUUID().toString().substring(0, 8));
    }

    public static Id restore(String id) {
        return new Id(id);
    }

    public String getValue() {
        return value;
    }
}
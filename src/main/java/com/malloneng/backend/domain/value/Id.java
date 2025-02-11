package com.malloneng.backend.domain.value;

import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
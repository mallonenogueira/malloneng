package com.malloneng.backend.application.usecase.dto;

import com.malloneng.backend.domain.value.Id;

import java.util.Objects;

public final class CreateSearchOutput {
    private final Id id;

    public CreateSearchOutput(
            Id id
    ) {
        this.id = id;
    }

    public Id id() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CreateSearchOutput) obj;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "CreateSearchOutput[" +
                "id=" + id + ']';
    }

}

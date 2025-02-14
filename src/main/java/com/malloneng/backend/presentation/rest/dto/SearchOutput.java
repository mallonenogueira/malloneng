package com.malloneng.backend.presentation.rest.dto;

import com.malloneng.backend.domain.value.Id;

import java.util.Objects;
import java.util.Set;

public final class SearchOutput {
    private final Id id;
    private final Set<String> urls;
    private final String status;

    public SearchOutput(
            Id id,
            Set<String> urls,
            String status
    ) {
        this.id = id;
        this.urls = urls;
        this.status = status;
    }

    public Id id() {
        return id;
    }

    public Set<String> urls() {
        return urls;
    }

    public String status() {
        return status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (SearchOutput) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.urls, that.urls) &&
                Objects.equals(this.status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, urls, status);
    }

    @Override
    public String toString() {
        return "SearchOutput[" +
                "id=" + id + ", " +
                "urls=" + urls + ", " +
                "status=" + status + ']';
    }

}

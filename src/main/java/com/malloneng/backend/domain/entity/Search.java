package com.malloneng.backend.domain.entity;

import com.malloneng.backend.domain.value.Id;

import java.util.HashSet;
import java.util.Set;

public class Search {
    private final Id id;
    private final Set<String> urls;
    private SearchStatus status;

    private Search(
            Id id, SearchStatus status, Set<String> urls) {
        this.id = id;
        this.status = status;
        this.urls = urls;
    }

    public static Search create() {
        return new Search(
                Id.create(),
                SearchStatus.ACTIVE,
                new HashSet<>()
        );
    }

    public static Search restore(
            String id,
            SearchStatus status,
            Set<String> urls
    ) {
        return new Search(
                Id.restore(id),
                status,
                urls
        );
    }

    public void finish() {
        if (!SearchStatus.ACTIVE.equals(this.status)) {
            // TODO: Adicionar Exception especifica
            throw new RuntimeException("Pesquisa já concluida");
        }

        this.status = SearchStatus.DONE;
    }

    public void addUrl(String url) {
        if (!SearchStatus.ACTIVE.equals(this.status)) {
            // TODO: Adicionar Exception especifica
            throw new RuntimeException("Pesquisa já concluida");
        }

        this.urls.add(url);
    }

    public Id getId() {
        return id;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public SearchStatus getStatus() {
        return status;
    }
}

package com.malloneng.backend.domain.entity;

import com.malloneng.backend.domain.SearchStatus;
import com.malloneng.backend.domain.value.Id;

import java.util.HashSet;
import java.util.Set;

public class Search {
    private final String id;
    private SearchStatus status;
    private final Set<String> urls;

    private Search(
            String id, SearchStatus status, Set<String> urls) {
        this.id = id;
        this.status = status;
        this.urls = urls;
    }

    public static Search create() {
        return new Search(
                Id.create().getValue(),
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
                id,
                status,
                urls
        );
    }

    public void finish() throws RuntimeException {
        if (!SearchStatus.ACTIVE.equals(this.status)) {
            // TODO: Adicionar Exception especifica
            throw new RuntimeException("Pesquisa já concluida");
        }

        this.status = SearchStatus.DONE;
    }

    public void addUrl(String url) {
        this.urls.add(url);
    }
}

package com.malloneng.backend.domain.entity;

import com.malloneng.backend.domain.exception.ApplicationException;
import com.malloneng.backend.domain.exception.InvalidKeywordsException;
import com.malloneng.backend.domain.exception.SearchDoneException;
import com.malloneng.backend.domain.value.Id;

import java.util.HashSet;
import java.util.Set;

public class Search {
    private final Id id;
    private final String keyword;
    private final Set<String> urls;
    private SearchStatus status;

    private Search(
            Id id, SearchStatus status, Set<String> urls, String keyword) {
        this.id = id;
        this.status = status;
        this.urls = urls;
        this.keyword = keyword;
    }

    public static Search create(String keyword) {
        if (!Search.isValidKeywords(keyword)) {
            throw new InvalidKeywordsException();
        }

        return new Search(
                Id.create(),
                SearchStatus.ACTIVE,
                new HashSet<>(),
                keyword
        );
    }

    public static Search restore(
            String id,
            SearchStatus status,
            Set<String> urls,
            String keyword
    ) {
        return new Search(
                Id.restore(id),
                status,
                urls,
                keyword
        );
    }

    public static boolean isValidKeywords(String keyword) {
        return keyword != null && keyword.length() >= 4 && keyword.length() <= 32;
    }

    public void finish() {
        if (!SearchStatus.ACTIVE.equals(this.status)) {
            throw new SearchDoneException();
        }

        this.status = SearchStatus.DONE;
    }

    public void addUrl(String url) {
        if (!SearchStatus.ACTIVE.equals(this.status)) {
            throw new SearchDoneException();
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

    public String getKeyword() {
        return keyword;
    }
}

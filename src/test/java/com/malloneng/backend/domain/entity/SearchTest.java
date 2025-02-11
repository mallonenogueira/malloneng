package com.malloneng.backend.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    @Test
    void shouldCreateSearch() {
        var search = Search.create();
        assertNotNull(search.getId());
        assertTrue(search.getUrls().isEmpty());
        assertEquals(SearchStatus.ACTIVE, search.getStatus());
    }

    @Test
    void shouldAddUrl() {
        var url = "http://asd";
        var search = Search.create();
        search.addUrl(url);
        assertTrue(search.getUrls().contains(url), "find url on search");
    }

    @Test
    void shouldFinishSearch() {
        var search = Search.create();
        search.finish();
        assertEquals(SearchStatus.DONE, search.getStatus());
    }

    @Test
    void shouldThrowErrorWhenSearchFinishedNotActiveStatus() {
        var search = Search.create();
        search.finish();
        assertThrows(RuntimeException.class, search::finish);
    }

    @Test
    void shouldThrowErrorWhenAddUrlWithFinishedSearch() {
        var url = "http://test";
        var search = Search.create();
        search.finish();
        assertThrows(RuntimeException.class, () -> search.addUrl(url));
    }
}

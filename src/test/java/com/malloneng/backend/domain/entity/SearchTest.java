package com.malloneng.backend.domain.entity;

import com.malloneng.backend.domain.stub.StubSearch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {

    @Test
    void shouldCreateSearch() {
        var search = StubSearch.aSearch();
        assertNotNull(search.getId());
        assertTrue(search.getUrls().isEmpty());
        assertEquals(SearchStatus.ACTIVE, search.getStatus());
    }

    @Test
    void shouldAddUrl() {
        var url = "http://asd";
        var search = StubSearch.aSearch();
        search.addUrl(url);
        assertTrue(search.getUrls().contains(url), "find url on search");
    }

    @Test
    void shouldFinishSearch() {
        var search = StubSearch.aSearch();
        search.finish();
        assertEquals(SearchStatus.DONE, search.getStatus());
    }

    @Test
    void shouldThrowErrorWhenSearchFinishedNotActiveStatus() {
        var search = StubSearch.aSearch();
        search.finish();
        assertThrows(RuntimeException.class, search::finish);
    }

    @Test
    void shouldThrowErrorWhenAddUrlWithFinishedSearch() {
        var url = "http://test";
        var search = StubSearch.aSearch();
        search.finish();
        assertThrows(RuntimeException.class, () -> search.addUrl(url));
    }
}

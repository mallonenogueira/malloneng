package com.malloneng.backend.domain.entity;

import com.malloneng.backend.domain.exception.SearchDoneException;
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
        assertEquals("active", search.getStatus().getText());
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
        assertEquals("done", search.getStatus().getText());
    }

    @Test
    void shouldThrowErrorWhenSearchFinishedNotActiveStatus() {
        var search = StubSearch.aSearch();
        search.finish();
        assertThrows(SearchDoneException.class, search::finish);
    }

    @Test
    void shouldThrowErrorWhenAddUrlWithFinishedSearch() {
        var url = "http://test";
        var search = StubSearch.aSearch();
        search.finish();
        assertThrows(SearchDoneException.class, () -> search.addUrl(url));
    }

    @Test
    void shouldThrowInvalidKeywordMin() {
        assertThrows(RuntimeException.class, () -> Search.create("012"));
    }

    @Test
    void shouldThrowInvalidKeywordMax() {
        assertThrows(RuntimeException.class, () -> Search.create("012345678901234567890123456789123"));
    }

    @Test
    void shouldCreateWithMaxKeyword() {
        assertNotNull(Search.create("01234567890123456789012345678912"));
    }
}

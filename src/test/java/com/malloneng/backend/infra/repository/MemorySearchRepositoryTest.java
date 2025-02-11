package com.malloneng.backend.infra.repository;

import com.malloneng.backend.domain.entity.Search;
import com.malloneng.backend.domain.entity.SearchStatus;
import com.malloneng.backend.infra.repository.memory.SearchRepositoryMemory;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemorySearchRepositoryTest {

    @Test
    void shouldSaveNewSearch() {
        var searchRepository = new SearchRepositoryMemory(new HashMap<>());
        var search = Search.create();
        searchRepository.create(search);
        var dbSearch = searchRepository.findById(search.getId()).orElseThrow();
        assertEquals(dbSearch.getId(), search.getId());
        assertEquals(SearchStatus.ACTIVE, dbSearch.getStatus());
    }

    @Test
    void shouldUpdateSearch() {
        var searchRepository = new SearchRepositoryMemory(new HashMap<>());
        var newSearch = Search.create();
        searchRepository.create(newSearch);

        var search = Search.restore(newSearch.getId().getValue(), newSearch.getStatus(), newSearch.getUrls());
        search.finish();
        searchRepository.update(search);

        var dbSearch = searchRepository.findById(search.getId()).orElseThrow();
        assertEquals(SearchStatus.DONE, dbSearch.getStatus());
    }

    @Test
    void shouldThrowNotFoundError() {
        var searchRepository = new SearchRepositoryMemory(new HashMap<>());
        var search = Search.create();
        assertThrows(RuntimeException.class, () -> searchRepository.update(search));
    }

    @Test
    void shouldThrowAlreadyExists() {
        var searchRepository = new SearchRepositoryMemory(new HashMap<>());
        var search = Search.create();
        searchRepository.create(search);
        assertThrows(RuntimeException.class, () -> searchRepository.create(search));
    }
}

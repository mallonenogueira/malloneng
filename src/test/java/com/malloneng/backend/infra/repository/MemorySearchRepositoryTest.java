package com.malloneng.backend.infra.repository;

import com.malloneng.backend.domain.entity.Search;
import com.malloneng.backend.domain.entity.SearchStatus;
import com.malloneng.backend.domain.stub.StubSearch;
import com.malloneng.backend.infra.repository.memory.MemorySearchRepository;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemorySearchRepositoryTest {

    @Test
    void shouldSaveNewSearch() {
        var searchRepository = new MemorySearchRepository(new HashMap<>());
        var search = StubSearch.aSearch();
        searchRepository.create(search);
        var dbSearch = searchRepository.findById(search.getId()).orElseThrow();
        assertEquals(dbSearch.getId(), search.getId());
        assertEquals(SearchStatus.ACTIVE, dbSearch.getStatus());
    }

    @Test
    void shouldUpdateSearch() {
        var searchRepository = new MemorySearchRepository(new HashMap<>());
        var newSearch = StubSearch.aSearch();
        searchRepository.create(newSearch);

        var search = Search.restore(
                newSearch.getId().getValue(),
                newSearch.getStatus(),
                newSearch.getUrls(),
                newSearch.getKeyword()
        );
        search.finish();
        searchRepository.update(search);

        var dbSearch = searchRepository.findById(search.getId()).orElseThrow();
        assertEquals(SearchStatus.DONE, dbSearch.getStatus());
    }

    @Test
    void shouldThrowNotFoundError() {
        var searchRepository = new MemorySearchRepository(new HashMap<>());
        var search = StubSearch.aSearch();
        assertThrows(RuntimeException.class, () -> searchRepository.update(search));
    }

    @Test
    void shouldThrowAlreadyExists() {
        var searchRepository = new MemorySearchRepository(new HashMap<>());
        var search = StubSearch.aSearch();
        searchRepository.create(search);
        assertThrows(RuntimeException.class, () -> searchRepository.create(search));
    }
}

package com.malloneng.backend.presentation.rest;

import com.malloneng.backend.application.usecase.CreateSearchUseCase;
import com.malloneng.backend.domain.exception.SearchNotFoundException;
import com.malloneng.backend.domain.stub.StubSearch;
import com.malloneng.backend.infra.repository.memory.MemorySearchRepository;
import com.malloneng.backend.presentation.http.HttpRequest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchControllerFindTest {

    @Test
    void shouldFindSearchById() {
        var stubSearch = StubSearch.aSearch();
        var searchRepository = new MemorySearchRepository(new HashMap<>());
        searchRepository.create(stubSearch);

        HttpRequest<String> request = mock(HttpRequest.class);
        when(request.getParam("id")).thenReturn(stubSearch.getId().getValue());

        var search = new SearchController(mock(CreateSearchUseCase.class), searchRepository).find(request);

        assertEquals(stubSearch.getId(), search.id());
        assertEquals(stubSearch.getStatus().getText(), search.status());
    }

    @Test
    void shouldNotFind() {
        HttpRequest<String> request = mock(HttpRequest.class);
        when(request.getParam("id")).thenReturn("0123456");
        var searchRepository = new MemorySearchRepository(new HashMap<>());
        var controller = new SearchController(mock(CreateSearchUseCase.class), searchRepository);

        assertThrows(SearchNotFoundException.class, () -> controller.find(request));
    }
}

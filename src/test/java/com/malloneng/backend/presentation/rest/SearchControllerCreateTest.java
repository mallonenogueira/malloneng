package com.malloneng.backend.presentation.rest;

import com.google.gson.Gson;
import com.malloneng.backend.application.usecase.CreateSearchUseCase;
import com.malloneng.backend.application.usecase.dto.CreateSearchInput;
import com.malloneng.backend.domain.event.EventEmitter;
import com.malloneng.backend.domain.exception.ApplicationException;
import com.malloneng.backend.infra.repository.memory.MemorySearchRepository;
import com.malloneng.backend.infra.spark.SparkHttpRequest;
import com.malloneng.backend.presentation.http.HttpRequest;
import org.junit.jupiter.api.Test;
import spark.Request;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;

class SearchControllerCreateTest {

    @Test
    void shouldSaveNewSearch() {
        var eventEmitter = mock(EventEmitter.class);
        HttpRequest<CreateSearchInput> request = mock(HttpRequest.class);
        when(request.getBody(CreateSearchInput.class)).thenReturn(new CreateSearchInput("012345"));

        var searchRepository = new MemorySearchRepository(new HashMap<>());
        var usecase = new CreateSearchUseCase(searchRepository, eventEmitter::emit);
        var createdSearch = new SearchController(usecase, searchRepository).create(request);

        verify(request, times(1)).getBody(CreateSearchInput.class);

        assertNotNull(searchRepository.findById(createdSearch.id()));
    }

    @Test
    void shouldThrowErrorWhenBodyIsNull() {
        var eventEmitter = mock(EventEmitter.class);
        var sparkRequest = mock(Request.class);
        when(sparkRequest.body()).thenReturn(null);
        HttpRequest<CreateSearchInput> request = new SparkHttpRequest<>(sparkRequest, new Gson());
        var searchRepository = new MemorySearchRepository(new HashMap<>());
        var usecase = new CreateSearchUseCase(searchRepository, eventEmitter::emit);

        assertThrows(ApplicationException.class, () -> new SearchController(usecase, searchRepository).create(request));
    }

    @Test
    void shouldThrowErrorWhenBodyIsEmpty() {
        var eventEmitter = mock(EventEmitter.class);
        var sparkRequest = mock(Request.class);
        when(sparkRequest.body()).thenReturn("");
        HttpRequest<CreateSearchInput> request = new SparkHttpRequest<>(sparkRequest, new Gson());
        var searchRepository = new MemorySearchRepository(new HashMap<>());
        var usecase = new CreateSearchUseCase(searchRepository, eventEmitter::emit);

        assertThrows(ApplicationException.class, () -> new SearchController(usecase, searchRepository).create(request));
    }
}

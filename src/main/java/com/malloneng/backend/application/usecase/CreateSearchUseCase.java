package com.malloneng.backend.application.usecase;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.application.usecase.dto.CreateSearchInput;
import com.malloneng.backend.application.usecase.dto.CreateSearchOutput;
import com.malloneng.backend.domain.entity.Search;
import com.malloneng.backend.domain.event.search.SearchCreatedEvent;
import com.malloneng.backend.domain.event.search.SearchEventEmitter;

public class CreateSearchUseCase {
    private final SearchRepository searchRepository;
    private final SearchEventEmitter eventEmitter;

    public CreateSearchUseCase(SearchRepository searchRepository, SearchEventEmitter eventEmitter) {
        this.searchRepository = searchRepository;
        this.eventEmitter = eventEmitter;
    }

    public CreateSearchOutput execute(CreateSearchInput input) {
        var search = Search.create(input.getKeyword());
        this.searchRepository.create(search);
        this.eventEmitter.emit(new SearchCreatedEvent(search.getId()));
        return new CreateSearchOutput(search.getId());
    }
}

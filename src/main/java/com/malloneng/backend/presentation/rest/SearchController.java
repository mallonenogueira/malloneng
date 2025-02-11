package com.malloneng.backend.presentation.rest;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.application.usecase.CreateSearchUseCase;
import com.malloneng.backend.application.usecase.dto.CreateSearchInput;
import com.malloneng.backend.application.usecase.dto.CreateSearchOutput;
import com.malloneng.backend.domain.exception.SearchNotFoundException;
import com.malloneng.backend.domain.value.Id;
import com.malloneng.backend.presentation.http.HttpMethod;
import com.malloneng.backend.presentation.http.HttpRequest;
import com.malloneng.backend.presentation.http.HttpRouterRegister;
import com.malloneng.backend.presentation.rest.dto.SearchOutput;

public class SearchController {
    private final CreateSearchUseCase createSearchUseCase;
    private final SearchRepository searchRepository;

    public SearchController(CreateSearchUseCase createSearchUseCase, SearchRepository searchRepository) {
        this.createSearchUseCase = createSearchUseCase;
        this.searchRepository = searchRepository;
    }

    public void register(HttpRouterRegister routerRegister) {
        routerRegister
                .register(HttpMethod.GET, "/crawl/:id", this::list)
                .register(HttpMethod.POST, "/crawl", this::create);
    }

    public SearchOutput list(HttpRequest<String> request) {
        var searchOptional = this.searchRepository.findById(Id.restore(request.getParam("id")));

        return searchOptional.map(search -> new SearchOutput(
                search.getId(),
                search.getUrls(),
                search.getStatus().getText()
        )).orElseThrow(SearchNotFoundException::new);
    }

    public CreateSearchOutput create(HttpRequest<CreateSearchInput> request) {
        return this.createSearchUseCase.execute(request.getBody(CreateSearchInput.class));
    }
}

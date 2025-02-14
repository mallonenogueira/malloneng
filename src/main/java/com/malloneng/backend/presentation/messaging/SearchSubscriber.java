package com.malloneng.backend.presentation.messaging;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.application.service.CrawlerService;
import com.malloneng.backend.application.service.FetchContentService;
import com.malloneng.backend.application.usecase.CrawlSearchUseCase;
import com.malloneng.backend.domain.event.Event;
import com.malloneng.backend.domain.value.Id;
import com.malloneng.backend.Env;

public class SearchSubscriber {
    private final EventSubscriber eventSubscriber;
    private final SearchRepository searchRepository;
    private final FetchContentService fetchContentService;
    private final CrawlerService crawler;

    public SearchSubscriber(EventSubscriber eventSubscriber,
                            SearchRepository searchRepository,
                            FetchContentService fetchContentService,
                            CrawlerService crawler
    ) {
        this.eventSubscriber = eventSubscriber;
        this.searchRepository = searchRepository;
        this.fetchContentService = fetchContentService;
        this.crawler = crawler;
    }

    public void subscribe(String baseUrl) {
        eventSubscriber.subscribe(Event.SEARCH_CREATED, event -> {
            if (event.getData() instanceof Id) {
                try {
                    new CrawlSearchUseCase(
                            new Env().getNumThreadRequests(), baseUrl, searchRepository, fetchContentService, crawler).execute((Id) event.getData());
                } catch (Exception e) {
                    //TODO: log
                    e.printStackTrace();
                }
            }
        });
    }

}

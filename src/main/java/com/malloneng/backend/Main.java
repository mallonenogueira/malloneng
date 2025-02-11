package com.malloneng.backend;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.application.usecase.CrawlingUrlUseCase;
import com.malloneng.backend.application.usecase.CreateSearchUseCase;
import com.malloneng.backend.domain.event.Event;
import com.malloneng.backend.domain.value.Id;
import com.malloneng.backend.infra.Configuration;
import com.malloneng.backend.infra.Env;
import com.malloneng.backend.infra.messaging.MemoryEventEmitter;
import com.malloneng.backend.presentation.rest.SearchController;

public class Main {
    public static void main(String[] args) {
        var config = new Configuration(new Env());
        var searchRepository = config.getSearchRepository();

        Main.createHttpServer(config, searchRepository);
        Main.createEventSubscribe(config, searchRepository);
    }

    private static void createHttpServer(Configuration config, SearchRepository searchRepository) {
        var gson = config.getGson();
        var routerRegister = config.getRouterRegister(gson);
        var eventEmitter = config.getEventEmitter();
        var createSearchUseCase = new CreateSearchUseCase(searchRepository, eventEmitter::emit);

        new SearchController(createSearchUseCase, searchRepository)
                .register(routerRegister);
    }

    private static void createEventSubscribe(Configuration config, SearchRepository searchRepository) {
        var env = new Env();

        new MemoryEventEmitter(env.getNumThreadEvents()).subscribe(Event.SEARCH_CREATED, event -> {
            if (event.getData() instanceof Id id) {
                try {
                    new CrawlingUrlUseCase(env.getBaseUrl(), searchRepository).execute(id);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

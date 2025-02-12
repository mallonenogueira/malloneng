package com.malloneng.backend;

import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.application.usecase.CreateSearchUseCase;
import com.malloneng.backend.infra.Configuration;
import com.malloneng.backend.infra.Env;
import com.malloneng.backend.presentation.messaging.SearchSubscriber;
import com.malloneng.backend.presentation.rest.SearchController;
import org.eclipse.jetty.util.StringUtil;

public class Main {
    public static void main(String[] args) {
        if (Main.verifyEnv()) return;

        var config = new Configuration();
        var searchRepository = config.getSearchRepository();

        Main.createHttpServer(config, searchRepository);
        Main.createEventSubscribe(config, searchRepository);
    }

    private static void createHttpServer(Configuration config, SearchRepository searchRepository) {
        var gson = config.getGson();
        var routerRegister = config.getRouterRegister(gson);
        var eventEmitter = config.getEventEmitter(new Env());
        var createSearchUseCase = new CreateSearchUseCase(searchRepository, eventEmitter::emit);

        new SearchController(createSearchUseCase, searchRepository)
                .register(routerRegister);
    }

    private static void createEventSubscribe(Configuration config, SearchRepository searchRepository) {
        new SearchSubscriber(
                config.getEventSubscriber(new Env()),
                searchRepository,
                config.getFetchContentService(),
                config.getCrawlerService()
        ).subscribe(new Env().getBaseUrl());
    }

    private static boolean verifyEnv() {
        var baseUrl = new Env().getBaseUrl();

        System.out.println("Adicione Env: BASE_URL");

        return StringUtil.isEmpty(baseUrl);
    }
}

package com.malloneng.backend.presentation.rest;

import com.malloneng.backend.presentation.http.HttpMethod;
import com.malloneng.backend.presentation.http.HttpRequest;
import com.malloneng.backend.presentation.http.HttpRouterRegister;

public class CrawlingController {
    public void register(HttpRouterRegister routerRegister) {
        routerRegister
                .register(HttpMethod.GET, "/crawl2/:id", this::list)
                .register(HttpMethod.POST, "/crawl2", this::create);
    }

    public String list(HttpRequest<Object> request) {
        return "Hellow " + request.getParam("id");
    }

    public String create(HttpRequest<Object> request) {
        return "Hellow " + request.getBody(Object.class);
    }
}

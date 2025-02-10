package com.malloneng.backend.presentation.rest;

import com.malloneng.backend.presentation.http.HttpRequest;

public class CrawlingController {
    public String list(HttpRequest<Object> request) {
        return "Hellow " + request.getParam("id");
    }

    public String create(HttpRequest<Object> request) {
        return "Hellow " + request.getBody(Object.class);
    }
}

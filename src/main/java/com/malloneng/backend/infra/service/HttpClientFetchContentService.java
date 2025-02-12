package com.malloneng.backend.infra.service;

import com.malloneng.backend.application.service.FetchContentService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientFetchContentService implements FetchContentService {
    private final HttpClient client;

    public HttpClientFetchContentService(HttpClient client) {
        this.client = client;
    }

    @Override
    public String fetch(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}

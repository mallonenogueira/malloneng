package com.malloneng.backend.infra.service;

import com.malloneng.backend.application.service.FetchContentService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpClientFetchContentService implements FetchContentService {
    private final HttpClient client;
    private final int timeout;

    public HttpClientFetchContentService(HttpClient client, int timeout) {
        this.client = client;
        this.timeout = timeout;
    }

    @Override
    public String fetch(URI uri) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().timeout(Duration.ofSeconds(timeout)) .uri(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}

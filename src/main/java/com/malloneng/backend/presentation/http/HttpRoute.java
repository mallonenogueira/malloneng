package com.malloneng.backend.presentation.http;

public class HttpRoute<B, R> {
    public final HttpMethod method;
    public final String endpoint;
    public final HttpHandler<B, R> handler;

    private HttpRoute(HttpMethod method, String endpoint, HttpHandler<B, R> handler) {
        this.method = method;
        this.endpoint = endpoint;
        this.handler = handler;
    }

    public static <B, R> HttpRoute<B, R> create(HttpMethod method, String endpoint, HttpHandler<B, R> handler) {
        return new HttpRoute<>(
                method,
                endpoint,
                handler
        );
    }
}

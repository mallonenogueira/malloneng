package com.malloneng.backend.presentation.http;

@FunctionalInterface
public interface HttpHandler<B, R> {
    R execute(HttpRequest<B> request);
}

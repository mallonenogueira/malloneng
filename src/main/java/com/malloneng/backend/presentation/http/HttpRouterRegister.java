package com.malloneng.backend.presentation.http;

public interface HttpRouterRegister {
    <R, B> HttpRouterRegister register(HttpRoute<R, B> route);
    <R, B> HttpRouterRegister register(HttpMethod method, String endpoint, HttpHandler<B, R> handler);
}
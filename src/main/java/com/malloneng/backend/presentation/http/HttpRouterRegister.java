package com.malloneng.backend.presentation.http;

public interface HttpRouterRegister {
    <R, B> HttpRouterRegister register(HttpRoute<R, B> route);
}
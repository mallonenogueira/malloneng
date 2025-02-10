package com.malloneng.backend.presentation.http;

public interface HttpRequest<T> {
    String getParam(String param);
    T getBody(Class<T> classOfT);
}

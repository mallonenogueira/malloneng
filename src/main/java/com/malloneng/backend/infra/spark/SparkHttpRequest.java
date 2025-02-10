package com.malloneng.backend.infra.spark;

import com.google.gson.Gson;
import com.malloneng.backend.presentation.http.HttpRequest;
import spark.Request;

public class SparkHttpRequest<T> implements HttpRequest<T> {

    private final Request request;

    public SparkHttpRequest(Request request) {
        this.request = request;
    }

    @Override
    public String getParam(String param) {
        return this.request.params(param);
    }

    @Override
    public T getBody(Class<T> classOfT) {
        return new Gson().fromJson(this.request.body(), classOfT);
    }
}
package com.malloneng.backend.infra.spark;

import com.google.gson.Gson;
import com.malloneng.backend.domain.exception.ApplicationException;
import com.malloneng.backend.presentation.http.HttpRequest;
import spark.Request;

public class SparkHttpRequest<T> implements HttpRequest<T> {

    private final Request request;
    private final Gson gson;

    public SparkHttpRequest(Request request, Gson gson) {
        this.request = request;
        this.gson = gson;
    }

    @Override
    public String getParam(String param) {
        return this.request.params(param);
    }

    @Override
    public T getBody(Class<T> classOfT) {
        var body = this.request.body();

        if (body == null || body.isEmpty()) {
            throw new ApplicationException("Request body é obrigatório.");
        }

        return gson.fromJson(body, classOfT);
    }
}
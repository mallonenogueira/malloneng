package com.malloneng.backend.infra.spark;

import com.google.gson.Gson;
import com.malloneng.backend.presentation.http.HttpHandler;
import com.malloneng.backend.presentation.http.HttpMethod;
import com.malloneng.backend.presentation.http.HttpRoute;
import com.malloneng.backend.presentation.http.HttpRouterRegister;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.post;

public class SparkRouterRegister implements HttpRouterRegister {
    private static final String ACCEPT_TYPE_JSON = "application/json";
    private final Gson gson;

    public SparkRouterRegister(Gson gson) {
        this.gson = gson;
        new SparkExceptionHandler(gson).handler();
    }

    @Override
    public <B, R> HttpRouterRegister register(HttpRoute<B, R> route) {
        Route handler = (req, res) -> {
            res.type(SparkRouterRegister.ACCEPT_TYPE_JSON);
            return route.handler.execute(new SparkHttpRequest<>(req));
        };

        if (route.method.equals(HttpMethod.GET)) {
            get(route.endpoint, SparkRouterRegister.ACCEPT_TYPE_JSON, handler, this.gson::toJson);
        } else if (route.method.equals(HttpMethod.POST)) {
            post(route.endpoint, SparkRouterRegister.ACCEPT_TYPE_JSON, handler, this.gson::toJson);
        }

        return this;
    }

    @Override
    public <R, B> HttpRouterRegister register(HttpMethod method, String endpoint, HttpHandler<B, R> handler) {
        return this.register(HttpRoute.create(
                method,
                endpoint,
                handler
        ));
    }
}
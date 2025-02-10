package com.malloneng.backend.infra.spark;

import com.malloneng.backend.presentation.http.HttpMethod;
import com.malloneng.backend.presentation.http.HttpRoute;
import com.malloneng.backend.presentation.http.HttpRouterRegister;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.post;

public class SparkRouterRegister implements HttpRouterRegister {
    @Override
    public <B, R> HttpRouterRegister register(HttpRoute<B, R> route) {
        Route handler = (req, res) -> route.handler.execute(new SparkHttpRequest<>(req));

        if (route.method.equals(HttpMethod.GET)) {
            get(route.endpoint, handler);
        } else if (route.method.equals(HttpMethod.POST)) {
            post(route.endpoint, handler);
        }

        return this;
    }
}
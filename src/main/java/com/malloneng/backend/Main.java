package com.malloneng.backend;

import com.malloneng.backend.infra.spark.SparkRouterRegister;
import com.malloneng.backend.presentation.http.HttpMethod;
import com.malloneng.backend.presentation.http.HttpRoute;
import com.malloneng.backend.presentation.rest.CrawlingController;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {
        get("/crawl/:id", (req, res) ->
                "GET /crawl/" + req.params("id"));
        post("/crawl", (req, res) ->
                "POST /crawl" + System.lineSeparator() + req.body());

        var routerRegister = new SparkRouterRegister();
        var crawlingController = new CrawlingController();

        routerRegister
                .register(HttpRoute.create(
                        HttpMethod.GET,
                        "/crawl2/:id",
                        crawlingController::list
                )).register(HttpRoute.create(
                        HttpMethod.POST,
                        "/crawl2",
                        crawlingController::create
                ));
    }
}

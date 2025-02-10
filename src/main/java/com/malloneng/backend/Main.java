package com.malloneng.backend;

import com.malloneng.backend.infra.spark.SparkRouterRegister;
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
        new CrawlingController(routerRegister);
    }
}

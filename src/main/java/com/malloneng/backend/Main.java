package com.malloneng.backend;

import com.malloneng.backend.infra.repository.memory.SearchRepositoryMemory;
import com.malloneng.backend.infra.spark.SparkRouterRegister;
import com.malloneng.backend.presentation.rest.CrawlingController;

import java.util.HashMap;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        get("/crawl/:id", (req, res) ->
                "GET /crawl/" + req.params("id"));
        post("/crawl", (req, res) ->
                "POST /crawl" + System.lineSeparator() + req.body());

        new SearchRepositoryMemory(new HashMap<>());
        var routerRegister = new SparkRouterRegister();
        new CrawlingController().register(routerRegister);
    }
}

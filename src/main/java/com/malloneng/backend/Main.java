package com.malloneng.backend;

import com.google.gson.Gson;
import com.malloneng.backend.domain.entity.Search;
import com.malloneng.backend.infra.repository.memory.SearchRepositoryMemory;
import com.malloneng.backend.infra.spark.SparkRouterRegister;
import com.malloneng.backend.presentation.rest.CrawlingController;

import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {
        get("/crawl/:id", (req, res) ->
                "GET /crawl/" + req.params("id"));
        post("/crawl", (req, res) ->
                "POST /crawl" + System.lineSeparator() + req.body());

        var searchRepo = new SearchRepositoryMemory(new HashMap<>());
        var routerRegister = new SparkRouterRegister();
        new CrawlingController().register(routerRegister);

        var search = Search.create();
        searchRepo.create(search);
        search.addUrl("http:13");
        search.addUrl("http:asd");
        searchRepo.update(Search.create());

        System.out.println(new Gson().toJson(searchRepo.findById(search.getId()).get()));
    }
}

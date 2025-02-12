package com.malloneng.backend.infra.spark;

import com.google.gson.Gson;
import com.malloneng.backend.domain.exception.ApplicationException;
import com.malloneng.backend.domain.exception.NotFoundException;

import static spark.Spark.exception;
import static spark.Spark.notFound;

public class SparkExceptionHandler {
    private static final String CONTENT_TYPE = "application/json";
    private final Gson gson;

    public SparkExceptionHandler(Gson gson) {
        this.gson = gson;
    }

    public void handler() {
        notFound((request, response) -> {
            response.type(SparkExceptionHandler.CONTENT_TYPE);
            response.status(404);

            return this.gson.toJson(new Response(
                    "Recurso não encontrado.",
                    "NotFound",
                    404
            ));
        });

        exception(NotFoundException.class, (e, request, response) -> {
            response.type(SparkExceptionHandler.CONTENT_TYPE);
            response.status(404);
            response.body(this.gson.toJson(new Response(
                    e.getMessage(),
                    e.getClass().getSimpleName(),
                    404
            )));
        });

        exception(ApplicationException.class, (e, request, response) -> {
            response.type(SparkExceptionHandler.CONTENT_TYPE);
            response.status(400);
            response.body(this.gson.toJson(new Response(
                    e.getMessage(),
                    e.getClass().getSimpleName(),
                    400
            )));
        });

        exception(Exception.class, (e, request, response) -> {
            response.type(SparkExceptionHandler.CONTENT_TYPE);
            response.status(500);
            // TODO: log
            e.printStackTrace();

            response.body(this.gson.toJson(new Response(
                    "Ocorreu algo inesperado.",
                    "InternalError",
                    500
            )));
        });
    }

    private static record Response(String message, String type, int statusCode) {
    }
}

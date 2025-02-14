package com.malloneng.backend.infra.spark;

import com.google.gson.Gson;
import com.malloneng.backend.domain.exception.ApplicationException;
import com.malloneng.backend.domain.exception.NotFoundException;

import java.util.Objects;

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

    private static final class Response {
        private final String message;
        private final String type;
        private final int statusCode;

        private Response(String message, String type, int statusCode) {
            this.message = message;
            this.type = type;
            this.statusCode = statusCode;
        }

        public String message() {
            return message;
        }

        public String type() {
            return type;
        }

        public int statusCode() {
            return statusCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Response) obj;
            return Objects.equals(this.message, that.message) &&
                    Objects.equals(this.type, that.type) &&
                    this.statusCode == that.statusCode;
        }

        @Override
        public int hashCode() {
            return Objects.hash(message, type, statusCode);
        }

        @Override
        public String toString() {
            return "Response[" +
                    "message=" + message + ", " +
                    "type=" + type + ", " +
                    "statusCode=" + statusCode + ']';
        }

        }
}

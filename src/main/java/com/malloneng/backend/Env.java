package com.malloneng.backend;

public class Env {
    public int getNumThreadEvents() {
        var numThreadEvent = System.getenv("NUM_THREAD_EVENT");

        if (numThreadEvent == null) {
            return 5;
        }

        return Integer.parseInt(numThreadEvent);
    }

    public int getNumThreadRequests() {
        var numThreadEvent = System.getenv("NUM_THREAD_REQUESTS");

        if (numThreadEvent == null) {
            return 20;
        }

        return Integer.parseInt(numThreadEvent);
    }


    public int getTimeout() {
        var timeout = System.getenv("TIMEOUT_SECONDS_CRAWLER");

        if (timeout == null) {
            return 30;
        }

        return Integer.parseInt(timeout);
    }


    public String getBaseUrl() {
        return System.getenv("BASE_URL");
    }
}

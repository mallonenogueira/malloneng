package com.malloneng.backend.infra;

public class Env {
    // TODO: Throw required env

    public int getNumThreadEvents() {
        var numThreadEvent = System.getenv("NUM_THREAD_EVENT");

        if (numThreadEvent == null) {
            return 10;
        }

        return Integer.parseInt(numThreadEvent);
    }

    public String getBaseUrl() {
        return System.getenv("BASE_URL");
    }
}

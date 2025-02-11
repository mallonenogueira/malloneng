package com.malloneng.backend.infra;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.malloneng.backend.application.repository.SearchRepository;
import com.malloneng.backend.domain.event.EventEmitter;
import com.malloneng.backend.domain.value.Id;
import com.malloneng.backend.infra.messaging.MemoryEventEmitter;
import com.malloneng.backend.infra.repository.memory.MemorySearchRepository;
import com.malloneng.backend.infra.serializer.IdDeserializer;
import com.malloneng.backend.infra.serializer.IdSerializer;
import com.malloneng.backend.infra.spark.SparkRouterRegister;
import com.malloneng.backend.presentation.http.HttpRouterRegister;

import java.util.HashMap;

public class Configuration {
    private final Env env;

    public Configuration(Env env) {
        this.env = env;
    }

    public Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Id.class, new IdSerializer())
                .registerTypeAdapter(Id.class, new IdDeserializer())
                .create();
    }

    public HttpRouterRegister getRouterRegister(Gson gson) {
        return new SparkRouterRegister(gson);
    }

    public SearchRepository getSearchRepository() {
        return new MemorySearchRepository(new HashMap<>());
    }

    public EventEmitter getEventEmitter() {
        return new MemoryEventEmitter(this.env.getNumThreadEvents());
    }
}

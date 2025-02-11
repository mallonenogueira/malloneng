package com.malloneng.backend.infra.messaging;

import com.malloneng.backend.domain.event.DomainEvent;
import com.malloneng.backend.domain.event.Event;
import com.malloneng.backend.domain.event.EventEmitter;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MemoryEventEmitter implements EventEmitter {
    private static final EnumMap<Event, Set<Consumer<DomainEvent>>> subscribers = new EnumMap<>(Event.class);
    private final ExecutorService executor;

    public MemoryEventEmitter(int threadPool) {
        executor = Executors.newFixedThreadPool(threadPool);
    }

    public void subscribe(Event event, Consumer<DomainEvent> subscriber) {
        MemoryEventEmitter.subscribers.computeIfAbsent(event, k -> new HashSet<>()).add(subscriber);
    }

    public void emit(DomainEvent event) {
        Set<Consumer<DomainEvent>> eventSubscribers = MemoryEventEmitter.subscribers.get(event.getEvent());

        if (eventSubscribers != null) {
            for (Consumer<DomainEvent> subscriber : eventSubscribers) {
                executor.submit(() -> {
                    subscriber.accept(event);
                });
            }
        }
    }
}
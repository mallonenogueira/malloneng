package com.malloneng.backend.domain.event;

@FunctionalInterface
public interface EventEmitter {
    void emit(DomainEvent event);
}

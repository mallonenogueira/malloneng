package com.malloneng.backend.presentation.messaging;

import com.malloneng.backend.domain.event.DomainEvent;
import com.malloneng.backend.domain.event.Event;

import java.util.function.Consumer;

public interface EventSubscriber {
    void subscribe(Event event, Consumer<DomainEvent> subscriber) ;
}

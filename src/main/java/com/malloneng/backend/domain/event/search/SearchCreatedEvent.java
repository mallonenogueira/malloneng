package com.malloneng.backend.domain.event.search;

import com.malloneng.backend.domain.event.DomainEvent;
import com.malloneng.backend.domain.event.Event;
import com.malloneng.backend.domain.value.Id;

import java.time.OffsetDateTime;

public class SearchCreatedEvent implements DomainEvent {
    private final Id searchId;
    private final OffsetDateTime createdAt = OffsetDateTime.now();

    public SearchCreatedEvent(Id searchId) {
        this.searchId = searchId;
    }

    @Override
    public Event getEvent() {
        return Event.SEARCH_CREATED;
    }

    @Override
    public Id getData() {
        return this.searchId;
    }

    @Override
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
}

package com.malloneng.backend.domain.event;

import java.time.OffsetDateTime;

public interface DomainEvent {
    Event getEvent();
    Object getData();
    default OffsetDateTime getCreatedAt() {
        return OffsetDateTime.now();
    }
}
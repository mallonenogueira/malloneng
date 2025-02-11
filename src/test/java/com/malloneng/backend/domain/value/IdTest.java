package com.malloneng.backend.domain.value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IdTest {
    @Test
    void shouldCreateId() {
        var newId = Id.create();
        var restoredId = Id.restore(newId.getValue());
        assertNotEquals(newId, Id.create());
        assertEquals(restoredId, newId);
    }
}

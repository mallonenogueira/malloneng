package com.malloneng.backend.infra.repository.serializer;

import com.malloneng.backend.domain.value.Id;
import com.malloneng.backend.infra.Configuration;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.*;

class IdSerializerTest {
    @Test
    void shouldSerializeId() {
        var gson = new Configuration().getGson();
        var id = Id.create();
        var jsonString = gson.toJson(id);
        assertEquals(MessageFormat.format("\"{0}\"", id.getValue()), jsonString);
    }
}

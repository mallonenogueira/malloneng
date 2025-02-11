package com.malloneng.backend.infra.repository.serializer;

import com.malloneng.backend.domain.value.Id;
import com.malloneng.backend.infra.Configuration;
import com.malloneng.backend.infra.Env;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdDeserializerTest {
    @Test
    void shouldDeserializeId() {
        var gson = new Configuration(new Env()).getGson();
        var id = Id.create();
        var jsonDeserialized = gson.fromJson(MessageFormat.format("\"{0}\"", id.getValue()), Id.class);
        assertEquals(id, jsonDeserialized);
    }
}

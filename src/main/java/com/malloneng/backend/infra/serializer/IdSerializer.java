package com.malloneng.backend.infra.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.malloneng.backend.domain.value.Id;

import java.lang.reflect.Type;

public class IdSerializer implements JsonSerializer<Id> {

    public JsonElement serialize(Id id, Type type,
                                 JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(id.getValue());
    }
}
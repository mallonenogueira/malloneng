package com.malloneng.backend.infra.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.malloneng.backend.domain.value.Id;

import java.lang.reflect.Type;

public class IdDeserializer implements JsonDeserializer<Id> {

    @Override
    public Id deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return Id.restore(jsonElement.getAsString());
    }
}
package com.framework.module.maintenance.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.ArrayList;

public class VehicleCategoryIdSerializer extends JsonSerializer {
    @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        if(o != null) {
            jsonGenerator.writeObject(String.valueOf(o).split(","));
        } else {
            jsonGenerator.writeObject(new ArrayList<>());
        }
    }
}

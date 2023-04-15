package com.app.dictionary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> String mapToJson(T obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T mapFromJson(String json, Class<T> targetClass) {
        try {
            return mapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

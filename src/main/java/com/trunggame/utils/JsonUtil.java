package com.trunggame.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class JsonUtil {
    private JsonUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String toJson(Object object) {
        ObjectMapper mapper = new ObjectMapper().setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonProcessingException ex) {
            log.error("ERROR", ex);
            jsonString = "Can't build json from object";
        }
        return jsonString;
    }
}
package io.cosmos.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonUtil {
    //
    private JsonUtil() {
        //
    }

    public static String toJson(Object target) {
        //
        String result = "";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            ObjectWriter writer = mapper.writer().withoutAttribute("logger");
            return writer.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        //
        T result = null;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            result = mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        //
        List<T> results = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            results = mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static <T> Set<T> fromJsonSet(String json, Class<T> clazz) {
        //
        Set<T> results = new HashSet<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(Set.class, clazz);
            results = mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return results;
    }
}


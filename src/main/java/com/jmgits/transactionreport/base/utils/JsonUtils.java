package com.jmgits.transactionreport.base.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jmgits.transactionreport.base.exception.GeneralException;

public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        MAPPER.setSerializationInclusion(Include.ALWAYS);
    }

    private JsonUtils() {

    }

    public static <T> T read(String value, Class<T> clazz) {
        try {
            return MAPPER.readValue(value, clazz);
        } catch (Exception var3) {

            var errorDescription = String.format("Could not read value '%s' as '%s'", value,
                clazz.getSimpleName());

            throw new GeneralException("GENERAL_EXCEPTION", errorDescription);
        }
    }

    public static String write(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (Exception var2) {

            var errorDescription = String.format("Could not write value '%s' as '%s'",
                value, var2);

            throw new GeneralException("GENERAL_EXCEPTION", errorDescription);
        }
    }

}

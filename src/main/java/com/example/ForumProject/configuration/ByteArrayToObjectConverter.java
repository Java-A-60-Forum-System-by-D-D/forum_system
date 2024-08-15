package com.example.ForumProject.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class ByteArrayToObjectConverter implements Converter<byte[], Object> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object convert(byte[] source) {
        try {
            return objectMapper.readValue(source, SecurityContextHolder.createEmptyContext()
                                                                       .getClass());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to deserialize SecurityContext", e);
        }
    }


}
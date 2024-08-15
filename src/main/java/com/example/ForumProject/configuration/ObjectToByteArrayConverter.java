package com.example.ForumProject.configuration;
import org.springframework.core.convert.converter.Converter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectToByteArrayConverter implements Converter<Object, byte[]> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] convert(Object source) {
        try {
            return objectMapper.writeValueAsBytes(source);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to serialize SecurityContext", e);
        }
    }
}
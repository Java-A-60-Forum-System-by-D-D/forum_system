package com.example.ForumProject.configuration;


import com.example.ForumProject.models.helpers.StringToTagConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToTagConverter stringToTagConverter;

    public WebConfig(StringToTagConverter stringToTagConverter) {
        this.stringToTagConverter = stringToTagConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToTagConverter);
    }
}
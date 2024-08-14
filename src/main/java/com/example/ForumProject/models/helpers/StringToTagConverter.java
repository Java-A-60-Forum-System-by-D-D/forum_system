package com.example.ForumProject.models.helpers;


import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.services.contracts.TagService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTagConverter implements Converter<String, Tag> {

    private final TagService tagService;

    public StringToTagConverter(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public Tag convert(String source) {
        return tagService.findOrCreateTagByName(source);
    }
}
package com.example.ForumProject.models.helpers;

import com.example.ForumProject.models.dto.TagDTO;
import com.example.ForumProject.models.persistentClasses.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    private final ModelMapper modelMapper;

    public TagMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Tag tagFromDTO(TagDTO tagDTO) {
        return modelMapper.map(tagDTO, Tag.class);

    }

    public Tag tagFromString(String tagDTO) {

        Tag tag = new Tag();
        tag.setName(tagDTO);
        return tag;
    }
}

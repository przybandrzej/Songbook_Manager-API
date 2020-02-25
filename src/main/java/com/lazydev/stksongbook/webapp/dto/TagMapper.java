package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDTO tagToTagDTO(Tag entity);

    Tag tagDTOToTag(TagDTO dto);
}

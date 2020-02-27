package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Tag;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TagMapper {

    //TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
    
    TagDTO tagToTagDTO(Tag entity);

    Tag tagDTOToTag(TagDTO dto);
}

package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.TagDTO;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TagMapper {

    TagDTO tagToTagDTO(Tag entity);
    Tag tagDTOToTag(TagDTO dto);
}

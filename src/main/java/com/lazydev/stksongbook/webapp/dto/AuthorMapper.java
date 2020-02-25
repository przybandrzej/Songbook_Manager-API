package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper( AuthorMapper.class );

    AuthorDTO authorToAuthorDTO(Author entity);
    Author authorDTOToAuthor(AuthorDTO dto);
}

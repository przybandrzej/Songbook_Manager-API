package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Author;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthorMapper {

    //AuthorMapper INSTANCE = Mappers.getMapper( AuthorMapper.class );

    AuthorDTO authorToAuthorDTO(Author entity);
    Author authorDTOToAuthor(AuthorDTO dto);
}

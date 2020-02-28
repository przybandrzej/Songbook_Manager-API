package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Author;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AuthorMapper {

    AuthorDTO authorToAuthorDTO(Author entity);
    Author authorDTOToAuthor(AuthorDTO dto);
}

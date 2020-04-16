package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.SongAuthorDTO;
import com.lazydev.stksongbook.webapp.api.mappers.decorator.SongAuthorDecorator;
import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(SongAuthorDecorator.class)
public interface SongAuthorMapper {

    SongAuthorDTO songsAuthorsEntityToSongAuthorDTO(SongAuthor entity);
    SongAuthor songsAuthorsEntityDTOToSongAuthor(SongAuthorDTO dto);
}

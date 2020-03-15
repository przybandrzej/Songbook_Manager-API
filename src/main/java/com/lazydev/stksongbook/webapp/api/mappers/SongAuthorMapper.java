package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.SongAuthorDTO;
import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SongAuthorMapper {

    SongAuthorDTO songsAuthorsEntityToSongAuthorDTO(SongAuthor entity);
    SongAuthor songsAuthorsEntityDTOToSongAuthor(SongAuthorDTO dto);
}

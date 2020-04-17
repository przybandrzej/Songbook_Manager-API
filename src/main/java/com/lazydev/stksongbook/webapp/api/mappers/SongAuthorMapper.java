package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.SongAuthorDTO;
import com.lazydev.stksongbook.webapp.api.mappers.decorator.SongAuthorMapperDecorator;
import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import com.lazydev.stksongbook.webapp.data.service.AuthorService;
import com.lazydev.stksongbook.webapp.data.service.SongService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {SongService.class, AuthorService.class})
@DecoratedWith(SongAuthorMapperDecorator.class)
public interface SongAuthorMapper {

  @Mapping(target = "songId", source = "song.id")
  @Mapping(target = "authorId", source = "author.id")
  SongAuthorDTO songsAuthorsEntityToSongAuthorDTO(SongAuthor entity);

  @Mapping(target = "song", ignore = true)
  @Mapping(target = "author", ignore = true)
  SongAuthor songsAuthorsEntityDTOToSongAuthor(SongAuthorDTO dto);
}

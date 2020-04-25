package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.service.dto.SongAuthorDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.SongAuthorMapperDecorator;
import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.service.SongService;
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

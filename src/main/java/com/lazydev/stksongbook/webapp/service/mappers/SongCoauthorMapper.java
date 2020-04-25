package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.SongCoauthorMapperDecorator;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.service.SongService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {SongService.class, AuthorService.class})
@DecoratedWith(SongCoauthorMapperDecorator.class)
public interface SongCoauthorMapper {

  @Mapping(target = "songId", source = "song.id")
  @Mapping(target = "authorId", source = "author.id")
  SongCoauthorDTO map(SongCoauthor entity);

  @Mapping(target = "song", ignore = true)
  @Mapping(target = "author", ignore = true)
  SongCoauthor map(SongCoauthorDTO dto);
}

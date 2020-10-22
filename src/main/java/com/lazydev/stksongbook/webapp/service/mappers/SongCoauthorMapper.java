package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SongCoauthorMapper {

  @Mapping(target = "songId", source = "song.id")
  @Mapping(target = "authorId", source = "author.id")
  SongCoauthorDTO map(SongCoauthor entity);
}

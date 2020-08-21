package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.SongEdit;
import com.lazydev.stksongbook.webapp.service.dto.SongEditDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.SongEditMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(SongEditMapperDecorator.class)
public interface SongEditMapper {

  @Mapping(target = "editedSong", source = "editedSong.id")
  @Mapping(target = "editedBy", source = "editedBy.id")
  SongEditDTO map(SongEdit entity);

  @Mapping(target = "editedSong", ignore = true)
  @Mapping(target = "editedBy", ignore = true)
  @Mapping(target = "timestamp", ignore = true)
  SongEdit map(SongEditDTO dto);
}

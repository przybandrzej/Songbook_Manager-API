package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.SongEdit;
import com.lazydev.stksongbook.webapp.service.dto.SongEditDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SongEditMapper {

  @Mapping(target = "editedSong", source = "editedSong.id")
  @Mapping(target = "editedBy", source = "editedBy.id")
  SongEditDTO map(SongEdit entity);
}

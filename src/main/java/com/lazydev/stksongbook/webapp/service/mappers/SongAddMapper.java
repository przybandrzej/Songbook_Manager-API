package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.SongAdd;
import com.lazydev.stksongbook.webapp.service.dto.SongAddDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SongAddMapper {

  @Mapping(target = "addedSong", source = "addedSong.id")
  @Mapping(target = "addedBy", source = "addedBy.id")
  SongAddDTO map(SongAdd entity);
}

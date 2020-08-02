package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.SongAdd;
import com.lazydev.stksongbook.webapp.service.dto.SongAddDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.SongAddMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(SongAddMapperDecorator.class)
public interface SongAddMapper {

  @Mapping(target = "addedSong", source = "addedSong.id")
  @Mapping(target = "addedBy", source = "addedBy.id")
  @Mapping(target = "timestamp", source = "timestamp", dateFormat = "dd-MM-yyyy HH:mm:ss")
  SongAddDTO map(SongAdd entity);

  @Mapping(target = "addedSong", ignore = true)
  @Mapping(target = "addedBy", ignore = true)
  @Mapping(target = "timestamp", ignore = true)
  SongAdd map(SongAddDTO dto);
}

package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.SongTimestamp;
import com.lazydev.stksongbook.webapp.service.dto.SongTimestampDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.SongTimestampDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(SongTimestampDecorator.class)
public interface SongTimestampMapper {

  @Mapping(target = "songId", source = "song.id")
  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "timestamp", source = "timestamp", dateFormat = "dd-MM-yyyy HH:mm:ss")
  SongTimestampDTO map(SongTimestamp entity);

  @Mapping(target = "song", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "timestamp", ignore = true)
  SongTimestamp map(SongTimestampDTO dto);
}

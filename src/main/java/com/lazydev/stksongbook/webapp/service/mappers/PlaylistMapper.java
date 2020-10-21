package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.PlaylistMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {UserService.class, SongService.class})
@DecoratedWith(PlaylistMapperDecorator.class)
public interface PlaylistMapper {

  @Mapping(target = "ownerId", source = "owner.id")
  @Mapping(target = "isPrivate", expression = "java(entity.isPrivate())")
  PlaylistDTO map(Playlist entity);

  @Mapping(target = "creationTime", ignore = true)
  @Mapping(target = "songs", ignore = true)
  @Mapping(target = "owner", ignore = true)
  Playlist map(PlaylistDTO dto);
}

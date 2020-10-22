package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PlaylistMapper {

  @Mapping(target = "ownerId", source = "owner.id")
  @Mapping(target = "isPrivate", expression = "java(entity.isPrivate())")
  PlaylistDTO map(Playlist entity);
}

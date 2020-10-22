package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserSongRatingMapper {

  @Mapping(target = "rating", source = "rating")
  @Mapping(target = "songId", source = "entity.song.id")
  @Mapping(target = "userId", source = "entity.user.id")
  UserSongRatingDTO map(UserSongRating entity);
}

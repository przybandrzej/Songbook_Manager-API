package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.UserSongRatingMapperDecorator;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {SongService.class, UserService.class})
@DecoratedWith(UserSongRatingMapperDecorator.class)
public interface UserSongRatingMapper {

    @Mapping(target="rating", source = "rating")
    @Mapping(target = "songId", source = "entity.song.id")
    @Mapping(target = "userId", source = "entity.user.id")
    UserSongRatingDTO map(UserSongRating entity);

    @Mapping(target="rating", source = "rating")
    @Mapping(target="user", ignore = true)
    @Mapping(target="song", ignore = true)
    UserSongRating map(UserSongRatingDTO dto);
}

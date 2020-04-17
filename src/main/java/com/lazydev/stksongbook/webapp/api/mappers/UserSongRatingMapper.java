package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.api.mappers.decorator.UserSongRatingMapperDecorator;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.data.service.SongService;
import com.lazydev.stksongbook.webapp.data.service.UserService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {SongService.class, UserService.class})
@DecoratedWith(UserSongRatingMapperDecorator.class)
public interface UserSongRatingMapper {

    @Mapping(target="userRating", source = "rating")
    @Mapping(target = "songId", source = "entity.song.id")
    @Mapping(target = "userId", source = "entity.user.id")
    UserSongRatingDTO usersSongsRatingsEntityToUserSongRatingDTO(UserSongRating entity);

    @Mapping(target="rating", source = "rating")
    @Mapping(target="user", ignore = true)
    @Mapping(target="song", ignore = true)
    UserSongRating usersSongsRatingsEntityDTOToUserSongRating(UserSongRatingDTO dto);
}

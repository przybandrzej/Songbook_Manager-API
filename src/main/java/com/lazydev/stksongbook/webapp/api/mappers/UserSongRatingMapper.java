package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.api.mappers.decorator.UserSongRatingDecorator;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import org.mapstruct.*;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(UserSongRatingDecorator.class)
public interface UserSongRatingMapper {

    @Mapping(target="userRating", source = "rating")
    @Mapping(target = "songId", source = "entity.song.id")
    @Mapping(target = "userId", source = "entity.user.id")
    UserSongRatingDTO usersSongsRatingsEntityToUserSongRatingDTO(UserSongRating entity);

    @Mapping(target="rating", source = "userRating")
    UserSongRating usersSongsRatingsEntityDTOToUserSongRating(UserSongRatingDTO dto);
}

package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserSongRatingMapper {

    @Mapping(target="userRating", source = "rating")
    @Mapping(target = "songId", source = "entity.song.id")
    UserSongRatingDTO usersSongsRatingsEntityToUserSongRatingDTO(UserSongRating entity);

    @Mapping(target="rating", source = "userRating")
    UserSongRating usersSongsRatingsEntityDTOToUserSongRating(UserSongRatingDTO dto);
}

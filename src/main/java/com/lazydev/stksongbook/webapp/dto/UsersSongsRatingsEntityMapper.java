package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.UsersSongsRatingsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UsersSongsRatingsEntityMapper {

    @Mappings({ @Mapping(target="userRating", source = "rating") })
    UsersSongsRatingsEntityDTO usersSongsRatingsEntityToUsersSongsRatingsEntityDTO(UsersSongsRatingsEntity entity);

    @Mappings({ @Mapping(target="rating", source = "userRating") })
    UsersSongsRatingsEntity usersSongsRatingsEntityDTOToUsersSongsRatingsEntity(UsersSongsRatingsEntityDTO dto);
}

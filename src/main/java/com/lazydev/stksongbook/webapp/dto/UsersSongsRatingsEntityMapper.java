package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.UsersSongsRatingsEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UsersSongsRatingsEntityMapper {

    //UsersSongsRatingsEntityMapper INSTANCE = Mappers.getMapper(UsersSongsRatingsEntityMapper.class);
    
    @Mappings({ @Mapping(target="userRating", source = "rating") })
    UsersSongsRatingsEntityDTO usersSongsRatingsEntityToUsersSongsRatingsEntityDTO(UsersSongsRatingsEntity entity);

    @Mappings({ @Mapping(target="rating", source = "userRating") })
    UsersSongsRatingsEntity usersSongsRatingsEntityDTOToUsersSongsRatingsEntity(UsersSongsRatingsEntityDTO dto);
}

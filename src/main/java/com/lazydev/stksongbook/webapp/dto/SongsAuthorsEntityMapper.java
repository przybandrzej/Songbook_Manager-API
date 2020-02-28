package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.SongsAuthorsEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SongsAuthorsEntityMapper {

    SongsAuthorsEntityDTO songsAuthorsEntityToSongsAuthorsEntityDTO(SongsAuthorsEntity entity);
    SongsAuthorsEntity songsAuthorsEntityDTOToSongsAuthorsEntity(SongsAuthorsEntityDTO dto);
}

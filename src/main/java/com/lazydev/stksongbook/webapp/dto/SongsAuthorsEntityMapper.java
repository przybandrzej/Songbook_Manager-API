package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.SongsAuthorsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SongsAuthorsEntityMapper {

    SongsAuthorsEntityDTO songsAuthorsEntityToSongsAuthorsEntityDTO(SongsAuthorsEntity entity);

    SongsAuthorsEntity songsAuthorsEntityDTOToSongsAuthorsEntity(SongsAuthorsEntityDTO dto);
}

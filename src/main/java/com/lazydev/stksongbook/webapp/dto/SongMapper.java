package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Song;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", /*uses = {SongsAuthorsEntityMapper.class, TagMapper.class, CategoryMapper.class},*/ injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SongMapper {

    //SongMapper INSTANCE = Mappers.getMapper(SongMapper.class);

    @Mappings({
            @Mapping(target="additionTime", source = "additionTime", dateFormat = "dd-MM-yyyy HH:mm:ss")/*,
            @Mapping(target="authors", ignore = true),
            @Mapping(target="category", ignore = true),
            @Mapping(target="tags", ignore = true)*/})
    SongDTO songToSongDTO(Song entity);

    @Mappings({
            @Mapping(target="additionTime", source = "additionTime", dateFormat = "dd-MM-yyyy HH:mm:ss") })
    Song songDTOToSong(SongDTO dto);
}

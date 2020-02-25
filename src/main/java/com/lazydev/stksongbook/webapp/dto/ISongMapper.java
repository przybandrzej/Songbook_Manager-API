package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Song;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

public interface ISongMapper {

    ISongMapper INSTANCE = Mappers.getMapper( ISongMapper.class );

    @Mappings({
            @Mapping(target="additionTime", source = "additionTime", dateFormat = "dd-MM-yyyy HH:mm:ss"),
            @Mapping(target = "averageRating", ignore = true) })
    SongDTO songToSongDTO(Song entity);

    @Mappings({
            @Mapping(target="additionTime", source = "additionTime", dateFormat = "dd-MM-yyyy HH:mm:ss"),
            @Mapping(target = "ratings", ignore = true) })
    Song songDTOToSong(SongDTO dto);
}

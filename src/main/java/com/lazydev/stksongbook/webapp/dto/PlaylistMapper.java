package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Playlist;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = ListSongMapper.class)
public interface PlaylistMapper {

    @Mappings({ @Mapping(target="creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss") })
    PlaylistDTO playlistToPlaylistDTO(Playlist entity);

    @Mappings({ @Mapping(target="creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss") })
    Playlist playlistDTOToPlaylist(PlaylistDTO dto);
}

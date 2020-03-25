package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PlaylistMapper {

    @Mapping(target="creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
    PlaylistDTO playlistToPlaylistDTO(Playlist entity);

    @Mapping(target="creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
    Playlist playlistDTOToPlaylist(PlaylistDTO dto);
}

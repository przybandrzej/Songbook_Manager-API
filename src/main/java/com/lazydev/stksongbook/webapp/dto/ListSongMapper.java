package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Playlist;
import com.lazydev.stksongbook.webapp.model.Song;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ListSongMapper {

    ListSongDTO playlistToPlaylistDTO(Song entity);
    Song playlistDTOToPlaylist(ListSongDTO dto);
}

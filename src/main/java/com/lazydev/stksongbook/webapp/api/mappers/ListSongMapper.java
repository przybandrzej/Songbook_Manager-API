package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.ListSongDTO;
import com.lazydev.stksongbook.webapp.data.model.Song;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ListSongMapper {

    ListSongDTO playlistToPlaylistDTO(Song entity);
    Song playlistDTOToPlaylist(ListSongDTO dto);
}

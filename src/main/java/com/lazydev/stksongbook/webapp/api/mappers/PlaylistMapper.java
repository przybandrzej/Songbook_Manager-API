package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.api.mappers.decorator.PlaylistMapperDecorator;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {User.class})
@DecoratedWith(PlaylistMapperDecorator.class)
public interface PlaylistMapper {

    @Mapping(target="creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "songs", expression = "java(getIds(entity.getSongs()))")
    @Mapping(target = "ownerId", source = "owner.id")
    PlaylistDTO playlistToPlaylistDTO(Playlist entity);

    @Mapping(target="creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "songs", ignore = true)
    Playlist playlistDTOToPlaylist(PlaylistDTO dto);

    default Set<Long> getIds(Set<Song> list) {
        return list.stream().map(Song::getId).collect(Collectors.toSet());
    }
}

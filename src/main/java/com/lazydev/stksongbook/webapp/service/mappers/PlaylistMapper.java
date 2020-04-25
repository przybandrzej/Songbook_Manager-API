package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.PlaylistMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {UserService.class, SongService.class})
@DecoratedWith(PlaylistMapperDecorator.class)
public interface PlaylistMapper {

    @Mapping(target="creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "songs", expression = "java(getIds(entity.getSongs()))")
    @Mapping(target = "ownerId", source = "owner.id")
    PlaylistDTO map(Playlist entity);

    @Mapping(target="creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "songs", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Playlist map(PlaylistDTO dto);

    @Mapping(target = "songs", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target="creationTime", ignore = true)
    Playlist map(CreatePlaylistDTO dto);

    default Set<Long> getIds(Set<Song> list) {
        if(list != null) {
            return list.stream().map(Song::getId).collect(Collectors.toSet());
        } else {
            return new HashSet<>();
        }
    }
}

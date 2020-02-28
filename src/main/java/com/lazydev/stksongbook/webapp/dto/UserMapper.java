package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Playlist;
import com.lazydev.stksongbook.webapp.model.Tag;
import com.lazydev.stksongbook.webapp.model.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {ListSongMapper.class, UsersSongsRatingsEntityMapper.class})
public interface UserMapper {

    @Mappings({
            @Mapping(target="userRoleId", expression = "java(entity.getUserRole().getId())"),
            @Mapping(target="playlistIds", expression = "java(convertPlaylistsToIDs(entity.getPlaylists()))"),
            @Mapping(target = "ratings", source = "userRatings") })
    UserDTO userToUserDTO(User entity);


    User userDTOToUser(UserDTO dto);

    default List<Long> convertPlaylistsToIDs(Set<Playlist> list) {
        return list.stream().mapToLong(Playlist::getId).boxed().collect(Collectors.toList());
    }

    /*default List<Tag> convertIDsToTags(List<Long> list) {
        //return list.stream().mapToLong(Tag::getId).boxed().collect(Collectors.toList());
    }*/
}

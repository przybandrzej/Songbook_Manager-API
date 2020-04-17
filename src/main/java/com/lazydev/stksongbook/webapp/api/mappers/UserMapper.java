package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.UserDTO;
import com.lazydev.stksongbook.webapp.api.mappers.decorator.UserMapperDecorator;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.service.SongService;
import com.lazydev.stksongbook.webapp.data.service.UserRoleService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {Song.class, SongService.class, UserRoleService.class})
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

  @Mapping(target = "userRoleId", expression = "java(entity.getUserRole().getId())")
  @Mapping(target = "songs", expression = "java(getIds(entity.getSongs()))")
  UserDTO userToUserDTO(User entity);

  @Mapping(target = "songs", ignore = true)
  @Mapping(target = "userRole", ignore = true)
  User userDTOToUser(UserDTO dto);

  default Set<Long> getIds(Set<Song> list) {
    return list.stream().map(Song::getId).collect(Collectors.toSet());
  }
}

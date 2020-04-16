package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.UserDTO;
import com.lazydev.stksongbook.webapp.api.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

public abstract class UserMapperDecorator implements UserMapper {

  @Autowired
  @Qualifier("delegate")
  private UserMapper delegate;

  @Override
  public User userDTOToUser(UserDTO dto) {
    var user = delegate.userDTOToUser(dto);
    var role = new UserRole();
    role.setId(dto.getUserRoleId());
    user.setUserRole(role);
    Set<Song> songs = new HashSet<>();
    dto.getSongs().forEach(s -> {
      var song = new Song();
      song.setId(s);
      songs.add(song);
    });
    user.setSongs(songs);
    return user;
  }
}

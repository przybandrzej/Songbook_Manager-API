package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.RegisterNewUserForm;
import com.lazydev.stksongbook.webapp.api.dto.UserDTO;
import com.lazydev.stksongbook.webapp.api.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.data.service.SongService;
import com.lazydev.stksongbook.webapp.data.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.stream.Collectors;

public abstract class UserMapperDecorator implements UserMapper {

  @Autowired
  @Qualifier("delegate")
  private UserMapper delegate;
  @Autowired
  private UserRoleService userRoleService;
  @Autowired
  private SongService songService;

  @Override
  public User userDTOToUser(UserDTO dto) {
    var user = delegate.userDTOToUser(dto);
    user.setUserRole(userRoleService.findById(dto.getUserRoleId()).orElse(null));
    user.setSongs(dto.getSongs().stream().map(s -> songService.findById(s).orElse(null)).collect(Collectors.toSet()));
    return user;
  }

  @Override
  public User registerFormToUser(RegisterNewUserForm form) {
    var user = delegate.registerFormToUser(form);
    user.setUserRole(userRoleService.findById(UserRole.CONST_USER_ID).orElse(null));
    user.setId(User.DEFAULT_ID);
    user.setSongs(new HashSet<>());
    user.setPlaylists(new HashSet<>());
    user.setUserRatings(new HashSet<>());
    return user;
  }
}

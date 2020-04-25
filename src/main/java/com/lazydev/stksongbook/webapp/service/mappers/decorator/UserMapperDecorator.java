package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.service.dto.RegisterNewUserForm;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserRoleService;
import com.lazydev.stksongbook.webapp.util.Constants;
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
  public User map(UserDTO dto) {
    var user = delegate.map(dto);
    user.setUserRole(userRoleService.findById(dto.getUserRoleId()).orElse(null));
    user.setSongs(dto.getSongs().stream().map(s -> songService.findById(s).orElse(null)).collect(Collectors.toSet()));
    return user;
  }

  @Override
  public User mapFromRegisterForm(RegisterNewUserForm form) {
    var user = delegate.mapFromRegisterForm(form);
    user.setUserRole(userRoleService.findById(UserRole.CONST_USER_ID).orElse(null));
    user.setId(Constants.DEFAULT_ID);
    user.setSongs(new HashSet<>());
    user.setPlaylists(new HashSet<>());
    user.setUserRatings(new HashSet<>());
    return user;
  }
}

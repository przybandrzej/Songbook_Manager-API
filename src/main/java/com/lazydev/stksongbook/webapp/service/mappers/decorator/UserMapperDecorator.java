package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.*;
import com.lazydev.stksongbook.webapp.service.dto.RegisterNewUserForm;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
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
  @Autowired
  private UserSongRatingService userSongRatingService;
  @Autowired
  private PlaylistService playlistService;
  @Autowired
  private UserService userService;

  @Override
  public User map(UserDTO dto) {
    var user = delegate.map(dto);
    var found = userService.findById(dto.getId());
    user.setUserRole(userRoleService.findById(dto.getUserRoleId()).orElse(null));
    user.setSongs(dto.getSongs().stream().map(s -> songService.findById(s).orElse(null)).collect(Collectors.toSet()));
    user.setUserRatings(new HashSet<>(userSongRatingService.findByUserId(dto.getId())));
    user.setPlaylists(new HashSet<>(playlistService.findByOwnerId(dto.getId())));
    user.setEmail(found.map(User::getEmail).orElse(null));
    user.setPassword(found.map(User::getPassword).orElse(null));
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

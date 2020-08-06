package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.service.*;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.stream.Collectors;

@Setter
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

  // todo change to transactional
  @Override
  public User map(UserDTO dto) {
    var user = delegate.map(dto);
    var found = userService.findById(dto.getId());
    user.setUserRole(userRoleService.findById(dto.getUserRoleId()));
    user.setSongs(dto.getSongs().stream().map(s -> songService.findById(s)).collect(Collectors.toSet()));
    user.setUserRatings(new HashSet<>(userSongRatingService.findByUserId(dto.getId())));
    user.setPlaylists(new HashSet<>(playlistService.findByOwnerId(dto.getId(), true)));
    user.setEmail(found.getEmail());
    user.setPassword(found.getPassword());
    user.setEditedSongs(found.getEditedSongs());
    user.setAddedSongs(found.getAddedSongs());
    user.setActivated(found.isActivated());
    user.setActivationKey(found.getActivationKey());
    user.setRegistrationDate(found.getRegistrationDate());
    user.setResetDate(found.getResetDate());
    user.setResetKey(found.getResetKey());
    return user;
  }
}

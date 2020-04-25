package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.data.model.UsersSongsRatingsKey;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserSongRatingMapperDecorator implements UserSongRatingMapper {

  @Autowired
  @Qualifier("delegate")
  private UserSongRatingMapper delegate;
  @Autowired
  private SongService songService;
  @Autowired
  private UserService userService;

  @Override
  public UserSongRating usersSongsRatingsEntityDTOToUserSongRating(UserSongRatingDTO dto) {
    var entity = delegate.usersSongsRatingsEntityDTOToUserSongRating(dto);
    entity.setSong(songService.findById(dto.getSongId()).orElse(null));
    entity.setUser(userService.findById(dto.getUserId()).orElse(null));
    entity.setId(new UsersSongsRatingsKey(dto.getUserId(), dto.getSongId()));
    return entity;
  }
}

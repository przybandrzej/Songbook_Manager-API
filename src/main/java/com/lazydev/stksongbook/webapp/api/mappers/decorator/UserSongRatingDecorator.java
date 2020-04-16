package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.api.mappers.UserSongRatingMapper;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class UserSongRatingDecorator implements UserSongRatingMapper {

  @Autowired
  @Qualifier("delegate")
  private UserSongRatingMapper delegate;

  @Override
  public UserSongRating usersSongsRatingsEntityDTOToUserSongRating(UserSongRatingDTO dto) {
    var entity = delegate.usersSongsRatingsEntityDTOToUserSongRating(dto);
    var song = new Song();
    song.setId(dto.getSongId());
    entity.setSong(song);
    var user = new User();
    user.setId(dto.getUserId());
    entity.setUser(user);
    return entity;
  }
}

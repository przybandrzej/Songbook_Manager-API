package com.lazydev.stksongbook.webapp.util.validators;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.exception.UserNotExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RelationsValidatior {

  private UserService userService;
  private SongService songService;

  public void validate(SongCoauthor entity) {

  }

  private void validateSongs(List<Long> songs) {
    songs.stream().allMatch(it -> songService.fi)
  }

  private void validateUser(Long id) {
    if(userService.findByIdNoException(id).isEmpty()) {
      throw new UserNotExistsException(id);
    }
  }
}

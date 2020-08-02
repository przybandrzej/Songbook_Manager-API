package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.SongTimestamp;
import com.lazydev.stksongbook.webapp.repository.SongTimestampRepository;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.SongTimestampDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongTimestampMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

@Setter
public abstract class SongTimestampDecorator implements SongTimestampMapper {

  @Autowired
  @Qualifier("delegate")
  private SongTimestampMapper delegate;
  @Autowired
  private UserService userService;
  @Autowired
  private SongService songService;
  @Autowired
  private SongTimestampRepository songTimestampRepository;

  @Override
  public SongTimestamp map(SongTimestampDTO dto) {
    SongTimestamp timestamp = delegate.map(dto);
    Optional<SongTimestamp> optionalSongTimestamp = songTimestampRepository.findById(dto.getId());
    optionalSongTimestamp.ifPresent(it -> timestamp.setTimestamp(it.getTimestamp()));
    timestamp.setSong(songService.findById(dto.getSongId()));
    timestamp.setUser(userService.findById(dto.getUserId()));
    return timestamp;
  }
}

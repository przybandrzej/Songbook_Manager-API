package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.SongAdd;
import com.lazydev.stksongbook.webapp.repository.SongAddRepository;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.SongAddDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongAddMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

@Setter
public abstract class SongAddMapperDecorator implements SongAddMapper {

  @Autowired
  @Qualifier("delegate")
  private SongAddMapper delegate;
  @Autowired
  private UserService userService;
  @Autowired
  private SongService songService;
  @Autowired
  private SongAddRepository songAddRepository;

  @Override
  public SongAdd map(SongAddDTO dto) {
    SongAdd timestamp = delegate.map(dto);
    Optional<SongAdd> optionalSongTimestamp = songAddRepository.findById(dto.getId());
    optionalSongTimestamp.ifPresent(it -> timestamp.setTimestamp(it.getTimestamp()));
    timestamp.setAddedSong(songService.findById(dto.getAddedSong()));
    timestamp.setAddedBy(userService.findById(dto.getAddedBy()));
    return timestamp;
  }
}

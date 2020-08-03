package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.SongEdit;
import com.lazydev.stksongbook.webapp.repository.SongEditRepository;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.SongEditDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongEditMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

@Setter
public abstract class SongEditMapperDecorator implements SongEditMapper {

  @Autowired
  @Qualifier("delegate")
  private SongEditMapper delegate;
  @Autowired
  private UserService userService;
  @Autowired
  private SongService songService;
  @Autowired
  private SongEditRepository songEditRepository;

  @Override
  public SongEdit map(SongEditDTO dto) {
    SongEdit timestamp = delegate.map(dto);
    Optional<SongEdit> optionalSongTimestamp = songEditRepository.findById(dto.getId());
    optionalSongTimestamp.ifPresent(it -> timestamp.setTimestamp(it.getTimestamp()));
    timestamp.setEditedSong(songService.findById(dto.getEditedSong()));
    timestamp.setEditedBy(userService.findById(dto.getEditedBy()));
    return timestamp;
  }
}

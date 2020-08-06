package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
public abstract class PlaylistMapperDecorator implements PlaylistMapper {

  @Autowired
  @Qualifier("delegate")
  private PlaylistMapper delegate;
  @Autowired
  private SongService songService;
  @Autowired
  private UserService userService;

  @Override
  public Playlist map(PlaylistDTO dto) {
    var playlist = delegate.map(dto);
    playlist.setOwner(userService.findById(dto.getOwnerId()));
    Set<Song> songs = new HashSet<>();
    dto.getSongs().forEach(s -> songs.add(songService.findById(s)));
    playlist.setSongs(songs);
    playlist.setPrivate(dto.getIsPrivate());
    return playlist;
  }

  @Override
  public Playlist map(CreatePlaylistDTO dto) {
    var playlist = delegate.map(dto);
    playlist.setOwner(userService.findById(dto.getOwnerId()));
    Set<Song> songs = new HashSet<>();
    dto.getSongs().forEach(s -> songs.add(songService.findById(s)));
    playlist.setSongs(songs);
    playlist.setId(Constants.DEFAULT_ID);
    playlist.setCreationTime(Instant.now());
    playlist.setPrivate(dto.getIsPrivate());
    return playlist;
  }
}

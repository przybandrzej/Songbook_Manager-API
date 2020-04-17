package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.api.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.service.SongService;
import com.lazydev.stksongbook.webapp.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

public abstract class PlaylistMapperDecorator implements PlaylistMapper {

  @Autowired
  @Qualifier("delegate")
  private PlaylistMapper delegate;
  @Autowired
  private SongService songService;
  @Autowired
  private UserService userService;

  @Override
  public Playlist playlistDTOToPlaylist(PlaylistDTO dto) {
    var playlist = delegate.playlistDTOToPlaylist(dto);
    playlist.setOwner(userService.findById(dto.getOwnerId()).orElse(null));
    Set<Song> songs = new HashSet<>();
    dto.getSongs().forEach(s -> {
      songs.add(songService.findById(s).orElse(null));
    });
    playlist.setSongs(songs);
    return playlist;
  }
}

package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.api.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class PlaylistMapperDecorator implements PlaylistMapper {

  @Autowired
  @Qualifier("delegate")
  private PlaylistMapper delegate;

  @Override
  public Playlist playlistDTOToPlaylist(PlaylistDTO dto) {
    var playlist = delegate.playlistDTOToPlaylist(dto);
    var user = new User();
    user.setId(dto.getOwnerId());
    playlist.setOwner(user);
    Set<Song> songs = new HashSet<>();
    dto.getSongs().forEach(s -> {
      var song = new Song();
      song.setId(s);
      songs.add(song);
    });
    playlist.setSongs(songs);
    return playlist;
  }

  @Override
  public PlaylistDTO playlistToPlaylistDTO(Playlist entity) {
    var dto = delegate.playlistToPlaylistDTO(entity);
    List<Long> songs = entity.getSongs().stream().map(Song::getId).collect(Collectors.toList());
    return PlaylistDTO.builder()
        .copy(dto)
        .isPrivate(entity.isPrivate())
        .ownerId(entity.getOwner().getId())
        .songs(songs)
        .create();
  }
}

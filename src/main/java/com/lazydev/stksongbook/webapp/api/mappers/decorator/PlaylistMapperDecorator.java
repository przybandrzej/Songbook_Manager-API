package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.api.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
    return playlist;
  }

  @Override
  public PlaylistDTO playlistToPlaylistDTO(Playlist entity) {
    var dto = delegate.playlistToPlaylistDTO(entity);
    return PlaylistDTO.builder()
        .copy(dto)
        .isPrivate(entity.isPrivate())
        .ownerId(entity.getOwner().getId())
        .create();
  }
}

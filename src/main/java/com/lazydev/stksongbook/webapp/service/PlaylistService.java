package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.repository.PlaylistRepository;
import com.lazydev.stksongbook.webapp.security.SecurityUtils;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlaylistService {

  private final PlaylistRepository repository;
  private final SongService songService;
  private final UserContextService userContextService;

  public Playlist findById(Long id) {
    var playlist = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Playlist.class, id));
    if(playlist.isPrivate()
        && playlist.getOwner() != userContextService.getCurrentUser()
        && (!SecurityUtils.isCurrentUserAdmin() || !SecurityUtils.isCurrentUserSuperuser())) {
      throw new ForbiddenOperationException("This playlist is private.");
    }
    return playlist;
  }

  public List<Playlist> findByNameFragment(String name, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByNameContainingIgnoreCaseAndIsPrivate(name, false);
    }
    if(!SecurityUtils.isCurrentUserAdmin() || !SecurityUtils.isCurrentUserSuperuser()) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findByNameContainingIgnoreCase(name);
  }

  public List<Playlist> findByNameFragment(String name, boolean includePrivate, int limit) {
    if(!includePrivate) {
      return repository.findByNameContainingIgnoreCaseAndIsPrivate(name, false, PageRequest.of(0, limit)).toList();
    }
    if(!SecurityUtils.isCurrentUserAdmin() || !SecurityUtils.isCurrentUserSuperuser()) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findByNameContainingIgnoreCase(name, PageRequest.of(0, limit)).toList();
  }

  public List<Playlist> findByOwnerId(Long id, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByOwnerIdAndIsPrivate(id, false);
    }
    if(!id.equals(userContextService.getCurrentUser().getId())
        && (!SecurityUtils.isCurrentUserAdmin() || !SecurityUtils.isCurrentUserSuperuser())) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findByOwnerId(id);
  }

  public List<Playlist> findBySongId(Long id, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findBySongsIdAndIsPrivate(id, false);
    }
    if(!SecurityUtils.isCurrentUserAdmin() || !SecurityUtils.isCurrentUserSuperuser()) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findBySongsId(id);
  }

  public List<Playlist> findAll(boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByIsPrivate(false);
    }
    if(!SecurityUtils.isCurrentUserAdmin() || !SecurityUtils.isCurrentUserSuperuser()) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findAll();
  }

  public List<Playlist> findAll(boolean includePrivate, int limit) {
    if(!includePrivate) {
      return repository.findByIsPrivate(false, PageRequest.of(0, limit)).toList();
    }
    if(!SecurityUtils.isCurrentUserAdmin() || !SecurityUtils.isCurrentUserSuperuser()) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findAll(PageRequest.of(0, limit)).toList();
  }

  public Playlist update(Playlist savePlaylist) {
    if(userContextService.getCurrentUser() != savePlaylist.getOwner()) {
      throw new ForbiddenOperationException("Cannot update playlist of another user.");
    }
    return repository.save(savePlaylist);
  }

  public void deleteById(Long id) {
    var playlist = findById(id);
    if(userContextService.getCurrentUser() != playlist.getOwner()) {
      throw new ForbiddenOperationException("Cannot delete playlist of another user.");
    }
    repository.deleteById(id);
  }

  public Playlist createPlaylist(CreatePlaylistDTO dto) {
    Playlist playlist = new Playlist();
    playlist.setId(Constants.DEFAULT_ID);
    playlist.setName(dto.getName());
    playlist.setPrivate(dto.getIsPrivate());
    playlist.setCreationTime(Instant.now());
    playlist.setOwner(userContextService.getCurrentUser());
    Set<Song> songs = new HashSet<>();
    dto.getSongs().forEach(s -> songs.add(songService.findById(s)));
    playlist.setSongs(songs);
    return repository.save(playlist);
  }
}

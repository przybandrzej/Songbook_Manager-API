package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.PlaylistRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@Validated
public class PlaylistService {

  private final PlaylistRepository repository;
  private final SongService songService;
  private final UserContextService userContextService;
  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;

  public PlaylistService(PlaylistRepository repository, SongService songService, UserContextService userContextService) {
    this.repository = repository;
    this.songService = songService;
    this.userContextService = userContextService;
  }

  public Playlist findById(Long id) {
    var playlist = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(Playlist.class, id));
    User currentUser = userContextService.getCurrentUser();
    if(playlist.isPrivate()
        && !playlist.getOwner().getId().equals(currentUser.getId())
        && (!currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName))) {
      throw new ForbiddenOperationException("This playlist is private.");
    }
    return playlist;
  }

  public List<Playlist> findByNameFragment(String name, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByNameContainingIgnoreCaseAndIsPrivate(name, false);
    }
    User currentUser = userContextService.getCurrentUser();
    if(!currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findByNameContainingIgnoreCase(name);
  }

  public List<Playlist> findByNameFragment(String name, boolean includePrivate, int limit) {
    if(!includePrivate) {
      return repository.findByNameContainingIgnoreCaseAndIsPrivate(name, false, PageRequest.of(0, limit)).toList();
    }
    User currentUser = userContextService.getCurrentUser();
    if(!currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findByNameContainingIgnoreCase(name, PageRequest.of(0, limit)).toList();
  }

  public List<Playlist> findAll(boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByIsPrivate(false);
    }
    User currentUser = userContextService.getCurrentUser();
    if(!currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findAll();
  }

  public List<Playlist> findAll(boolean includePrivate, int limit) {
    if(!includePrivate) {
      return repository.findByIsPrivate(false, PageRequest.of(0, limit)).toList();
    }
    User currentUser = userContextService.getCurrentUser();
    if(!currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findAll(PageRequest.of(0, limit)).toList();
  }

  public Playlist update(@Valid PlaylistDTO savePlaylist) {
    Playlist playlist = findById(savePlaylist.getId());
    User currentUser = userContextService.getCurrentUser();
    if(!playlist.getOwner().getId().equals(currentUser.getId()) &&
        !currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("No permission.");
    }
    playlist.setPrivate(savePlaylist.getIsPrivate());
    playlist.setName(savePlaylist.getName());
    return repository.save(playlist);
  }

  public void deleteById(Long id) {
    var playlist = findById(id);
    User currentUser = userContextService.getCurrentUser();
    if(!playlist.getOwner().getId().equals(currentUser.getId())
        && !(currentUser.getUserRole().getName().equals(superuserRoleName)
        || currentUser.getUserRole().getName().equals(adminRoleName))) {
      throw new ForbiddenOperationException("Playlists can be created only by its author or admin.");
    }
    repository.deleteById(id);
  }

  public Playlist createPlaylist(@Valid CreatePlaylistDTO dto, User user) {
    User currentUser = userContextService.getCurrentUser();
    if(!user.getId().equals(currentUser.getId())
        && !(currentUser.getUserRole().getName().equals(superuserRoleName)
        || currentUser.getUserRole().getName().equals(adminRoleName))) {
      throw new ForbiddenOperationException("Playlists can be created only by its author or admin.");
    }
    Playlist playlist = new Playlist();
    playlist.setId(Constants.DEFAULT_ID);
    playlist.setName(dto.getName());
    playlist.setPrivate(dto.getIsPrivate());
    playlist.setCreationTime(Instant.now());
    user.addPlaylist(playlist);
    dto.getSongs().forEach(s -> playlist.addSong(songService.findById(s)));
    return repository.save(playlist);
  }

  public void addSong(Long playlistId, Long songId) {
    var playlist = findById(playlistId);
    User currentUser = userContextService.getCurrentUser();
    if(!playlist.getOwner().getId().equals(currentUser.getId()) &&
        !currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("No permission.");
    }
    Song song = songService.findById(songId);
    playlist.addSong(song);
    repository.save(playlist);
  }

  public void removeSong(Long playlistId, Long songId) {
    var playlist = findById(playlistId);
    User currentUser = userContextService.getCurrentUser();
    if(!playlist.getOwner().getId().equals(currentUser.getId()) && !currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("No permission.");
    }
    Song song = songService.findById(songId);
    playlist.removeSong(song);
    repository.save(playlist);
  }
}

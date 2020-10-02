package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.PlaylistRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
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

  public List<Playlist> findByOwnerId(Long id, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByOwnerIdAndIsPrivate(id, false);
    }
    User currentUser = userContextService.getCurrentUser();
    if(!id.equals(currentUser.getId())
        && (!currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName))) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findByOwnerId(id);
  }

  public List<Playlist> findBySongId(Long id, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findBySongsIdAndIsPrivate(id, false);
    }
    User currentUser = userContextService.getCurrentUser();
    if(!currentUser.getUserRole().getName().equals(superuserRoleName)
        && !currentUser.getUserRole().getName().equals(adminRoleName)) {
      throw new ForbiddenOperationException("Cannot get private playlists.");
    }
    return repository.findBySongsId(id);
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

  public Playlist update(Playlist savePlaylist) {
    if(!userContextService.getCurrentUser().getId().equals(savePlaylist.getOwner().getId())) {
      throw new ForbiddenOperationException("Cannot update playlist of another user.");
    }
    return repository.save(savePlaylist);
  }

  public void deleteById(Long id) {
    var playlist = findById(id);
    if(!userContextService.getCurrentUser().getId().equals(playlist.getOwner().getId())) {
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

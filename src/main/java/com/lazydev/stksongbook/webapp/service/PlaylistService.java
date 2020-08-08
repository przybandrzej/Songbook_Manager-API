package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.repository.PlaylistRepository;
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
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlaylistService {

  private final PlaylistRepository repository;
  private final SongService songService;
  private final UserContextService userContextService;

  public Optional<Playlist> findByIdNoException(Long id, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByIdAndIsPrivate(id, false);
    }
    return repository.findById(id);
  }

  public Playlist findById(Long id, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByIdAndIsPrivate(id, false)
          .orElseThrow(() -> new EntityNotFoundException(Playlist.class, id));
    }
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Playlist.class, id));
  }

  public List<Playlist> findByNameFragment(String name, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByNameContainingIgnoreCaseAndIsPrivate(name, false);
    }
    return repository.findByNameContainingIgnoreCase(name);
  }

  public List<Playlist> findByNameFragment(String name, boolean includePrivate, int limit) {
    if(!includePrivate) {
      return repository.findByNameContainingIgnoreCaseAndIsPrivate(name, false, PageRequest.of(0, limit)).toList();
    }
    return repository.findByNameContainingIgnoreCase(name, PageRequest.of(0, limit)).toList();
  }

  public List<Playlist> findByOwnerId(Long id, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByOwnerIdAndIsPrivate(id, false);
    }
    return repository.findByOwnerId(id);
  }

  public List<Playlist> findBySongId(Long id, boolean includePrivate) {
    if(!includePrivate) {
      return repository.findBySongsIdAndIsPrivate(id, false);
    }
    return repository.findBySongsId(id);
  }

  public List<Playlist> findAll(boolean includePrivate) {
    if(!includePrivate) {
      return repository.findByIsPrivate(false);
    }
    return repository.findAll();
  }

  public List<Playlist> findAll(boolean includePrivate, int limit) {
    if(!includePrivate) {
      return repository.findByIsPrivate(false, PageRequest.of(0, limit)).toList();
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
    var playlist = findById(id, true);
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

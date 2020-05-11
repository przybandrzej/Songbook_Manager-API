package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.repository.PlaylistRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlaylistService {

  private PlaylistRepository repository;

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

  public Playlist save(Playlist saveAuthor) {
    return repository.save(saveAuthor);
  }

  public void deleteById(Long id) {
    findById(id, true);
    repository.deleteById(id);
  }
}

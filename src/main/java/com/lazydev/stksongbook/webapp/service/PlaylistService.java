package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.repository.PlaylistRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlaylistService {

  private PlaylistRepository repository;

  public Optional<Playlist> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public Playlist findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Playlist.class, id));
  }

  public List<Playlist> findByName(String name) {
    return repository.findByNameIgnoreCase(name);
  }

  public List<Playlist> findByOwnerId(Long id) {
    return repository.findByOwnerId(id);
  }

  public List<Playlist> findBySongId(Long id) {
    return repository.findBySongsId(id);
  }

  public List<Playlist> findAll() {
    return repository.findAll();
  }

  public Playlist save(Playlist saveAuthor) {
    return repository.save(saveAuthor);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}

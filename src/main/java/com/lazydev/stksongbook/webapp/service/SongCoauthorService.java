package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.repository.SongCoauthorRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SongCoauthorService {

  private SongCoauthorRepository repository;

  public Optional<SongCoauthor> findBySongIdAndAuthorIdNoException(Long songId, Long authorId) {
    return repository.findBySongIdAndAuthorId(songId, authorId);
  }

  public SongCoauthor findBySongIdAndAuthorId(Long songId, Long authorId) {
    return repository.findBySongIdAndAuthorId(songId, authorId)
        .orElseThrow(() -> new EntityNotFoundException(SongCoauthor.class));
  }

  public List<SongCoauthor> findAll() {
    return repository.findAll();
  }

  public List<SongCoauthor> findBySongId(Long songId) {
    return repository.findBySongId(songId);
  }

  public List<SongCoauthor> findByAuthorId(Long id) {
    return repository.findByAuthorId(id);
  }

  public List<SongCoauthor> findByFunction(String function) {
    return repository.findByFunctionContainingIgnoreCase(function);
  }

  public SongCoauthor save(SongCoauthor songCoauthor) {
    return repository.save(songCoauthor);
  }

  public void delete(SongCoauthor songCoauthor) {
    repository.delete(songCoauthor);
  }
}

package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.repository.SongCoauthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SongCoauthorService {

  private SongCoauthorRepository repository;

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
    return repository.findByFunctionIgnoreCase(function);
  }

  public SongCoauthor save(SongCoauthor songCoauthor) {
    return repository.save(songCoauthor);
  }

  public void delete(SongCoauthor songCoauthor) {
    repository.delete(songCoauthor);
  }
}

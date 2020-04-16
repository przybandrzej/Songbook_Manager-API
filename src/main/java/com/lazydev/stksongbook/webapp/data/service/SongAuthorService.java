package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import com.lazydev.stksongbook.webapp.data.repository.SongAuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SongAuthorService {

  private SongAuthorRepository repository;

  public List<SongAuthor> findAll() {
    return repository.findAll();
  }

  public List<SongAuthor> findBySongId(Long songId) {
    return repository.findBySongId(songId);
  }

  public List<SongAuthor> findByAuthorId(Long id) {
    return repository.findByAuthorId(id);
  }

  public List<SongAuthor> findByFunction(String function) {
    return repository.findByFunctionIgnoreCase(function);
  }

  public SongAuthor save(SongAuthor songAuthor) {
    return repository.save(songAuthor);
  }

  public void delete(SongAuthor songAuthor) {
    repository.delete(songAuthor);
  }
}

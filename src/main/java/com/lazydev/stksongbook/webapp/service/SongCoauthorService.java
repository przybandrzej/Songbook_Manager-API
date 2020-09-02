package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.data.model.SongsCoauthorsKey;
import com.lazydev.stksongbook.webapp.data.model.enumeration.CoauthorFunction;
import com.lazydev.stksongbook.webapp.repository.SongCoauthorRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SongCoauthorService {

  private final SongCoauthorRepository repository;

  public Optional<SongCoauthor> findBySongIdAndAuthorIdNoException(Long songId, Long authorId) {
    return repository.findBySongIdAndAuthorId(songId, authorId);
  }

  public SongCoauthor findBySongIdAndAuthorId(Long songId, Long authorId) {
    return repository.findBySongIdAndAuthorId(songId, authorId)
        .orElseThrow(() -> new EntityNotFoundException(SongCoauthor.class));
  }

  public SongCoauthor findBySongIdAndAuthorIdAndFunction(Long songId, Long authorId, CoauthorFunction function) {
    return repository.findBySongIdAndAuthorIdAndCoauthorFunction(songId, authorId, function)
        .orElseThrow(() -> new EntityNotFoundException(SongCoauthor.class));
  }

  public Optional<SongCoauthor> findBySongIdAndAuthorIdAndFunctionNoException(Long songId, Long authorId, CoauthorFunction function) {
    return repository.findBySongIdAndAuthorIdAndCoauthorFunction(songId, authorId, function);
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

  public List<SongCoauthor> findByFunction(CoauthorFunction function) {
    return repository.findByCoauthorFunction(function);
  }

  public SongCoauthor save(SongCoauthor songCoauthor) {
    return repository.save(songCoauthor);
  }

  public void delete(SongCoauthor songCoauthor) {
    findBySongIdAndAuthorIdAndFunction(songCoauthor.getSong().getId(), songCoauthor.getAuthor().getId(), songCoauthor.getCoauthorFunction());
    songCoauthor.getSong().removeCoauthor(songCoauthor);
    songCoauthor.getAuthor().removeCoauthor(songCoauthor);
    repository.delete(songCoauthor);
  }

  public SongCoauthor findOrCreate(Song song, Author author, CoauthorFunction function) {
    var coauthor = new SongCoauthor();
    coauthor.setId(new SongsCoauthorsKey());
    coauthor.setAuthor(author);
    coauthor.setSong(song);
    coauthor.setCoauthorFunction(function);
    return repository.save(coauthor);
  }
}

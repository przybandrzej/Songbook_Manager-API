package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.data.model.SongsCoauthorsKey;
import com.lazydev.stksongbook.webapp.data.model.enumeration.CoauthorFunction;
import com.lazydev.stksongbook.webapp.repository.AuthorRepository;
import com.lazydev.stksongbook.webapp.repository.SongCoauthorRepository;
import com.lazydev.stksongbook.webapp.repository.SongRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SongCoauthorService {

  private final SongCoauthorRepository repository;
  private final SongRepository songRepository;
  private final AuthorRepository authorRepository;

  public List<SongCoauthor> findBySongIdAndAuthorId(Long songId, Long authorId) {
    return repository.findBySongIdAndAuthorId(songId, authorId);
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

  public void delete(Long songId, Long authorId, String function) {
    List<SongCoauthor> list = findBySongIdAndAuthorId(songId, authorId);
    if(list.stream().noneMatch(it -> it.getCoauthorFunction().toString().equals(function))) {
      return;
    }
    SongCoauthor coauthor = list.stream().filter(it -> it.getCoauthorFunction().toString().equals(function)).findFirst().orElseThrow(() -> new EntityNotFoundException(SongCoauthor.class));
    songRepository.findById(songId).orElseThrow(() -> new EntityNotFoundException(Song.class, songId)).removeCoauthor(coauthor);
    authorRepository.findById(authorId).orElseThrow(() -> new EntityNotFoundException(Author.class, authorId)).removeCoauthor(coauthor);
    repository.delete(coauthor);
  }

  public void delete(SongCoauthor coauthor) {
    List<SongCoauthor> list = findBySongIdAndAuthorId(coauthor.getSong().getId(), coauthor.getAuthor().getId());
    if(list.stream().noneMatch(it -> it.getCoauthorFunction().equals(coauthor.getCoauthorFunction()))) {
      return;
    }
    coauthor.getSong().removeCoauthor(coauthor);
    coauthor.getAuthor().removeCoauthor(coauthor);
    repository.delete(coauthor);
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

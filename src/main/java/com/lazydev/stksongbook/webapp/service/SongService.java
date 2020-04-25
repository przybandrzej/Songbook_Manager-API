package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.repository.SongRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SongService {

  private SongRepository repository;

  public List<Song> findAll() {
    return repository.findAll();
  }

  public Optional<Song> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public Song findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Song.class, id));
  }

  public List<Song> findByTitle(String val) {
    return repository.findByTitleIgnoreCase(val);
  }

  public List<Song> findByTitleContains(String val) {
    return repository.findByTitleContainingIgnoreCase(val);
  }

  public List<Song> findByLyricsContains(String val) {
    return repository.findByLyricsContainingIgnoreCase(val);
  }

  public List<Song> findByAuthorId(Long authorId) {
    return repository.findByAuthorId(authorId);
  }

  public List<Song> findByCategoryId(Long id) {
    return repository.findByCategoryId(id);
  }

  public List<Song> findByTagId(Long id) {
    return repository.findByTagsId(id);
  }

  public List<Song> findByRatingEqualGreater(Double val) {
    return repository.findByRatingsRatingGreaterThanEqual(val);
  }

  public List<Song> findByRatingEqualLess(Double val) {
    return repository.findByRatingsRatingLessThanEqual(val);
  }

  public List<Song> findByCreationTimeEqualGreater(LocalDateTime val) {
    return repository.findByCreationTimeGreaterThanEqual(val);
  }

  public List<Song> findByCreationTimeEqualLess(LocalDateTime val) {
    return repository.findByCreationTimeLessThanEqual(val);
  }

  public Song save(Song saveSong) {
    return repository.save(saveSong);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}

package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.repository.SongRepository;
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

  public Optional<Song> findById(Long id) {
    return repository.findById(id);
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
    return repository.findByAuthorsIdAuthorId(authorId);
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

  public List<Song> findByAdditionTimeEqualGreater(LocalDateTime val) {
    return repository.findByAdditionTimeGreaterThanEqual(val);
  }

  public List<Song> findByAdditionTimeEqualLess(LocalDateTime val) {
    return repository.findByAdditionTimeLessThanEqual(val);
  }

  public Song save(Song saveSong) {
    return repository.save(saveSong);
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }
}

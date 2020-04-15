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

  private SongRepository dao;

  public List<Song> findAll() {
    return dao.findAll();
  }

  public Optional<Song> findById(Long id) {
    return dao.findById(id);
  }

  public List<Song> findByTitle(String val) {
    return dao.findByTitle(val);
  }

  public List<Song> findByTitleContains(String val) {
    return dao.findByTitleContaining(val);
  }

  public List<Song> findByLyricsContains(String val) {
    return dao.findByLyricsContaining(val);
  }

  public List<Song> findByAuthorId(Long authorId) {
    return dao.findByAuthorsIdAuthorId(authorId);
  }

  public List<Song> findByCategoryId(Long id) {
    return dao.findByCategoryId(id);
  }

  public List<Song> findByTagId(Long id) {
    return dao.findByTagsId(id);
  }

  public List<Song> findByRatingEqualGreater(Double val) {
    return dao.findByRatingsRatingGreaterThanEqual(val);
  }

  public List<Song> findByRatingEqualLess(Double val) {
    return dao.findByRatingsRatingLessThanEqual(val);
  }

  public List<Song> findByAdditionTimeEqualGreater(LocalDateTime val) {
    return dao.findByAdditionTimeGreaterThanEqual(val);
  }

  public List<Song> findByAdditionTimeEqualLess(LocalDateTime val) {
    return dao.findByAdditionTimeLessThanEqual(val);
  }

  public Song save(Song saveSong) {
    return dao.save(saveSong);
  }

  public void deleteById(Long id) {
    dao.deleteById(id);
  }
}

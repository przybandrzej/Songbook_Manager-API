package com.lazydev.stksongbook.webapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.repository.SongRepository;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
@Validated
public class SongService {

  private SongRepository repository;
  private TagService tagService;
  private AuthorService authorService;
  private SongCoauthorService coauthorService;
  private CategoryService categoryService;
  private FileSystemStorageService storageService;
  private UserSongRatingService ratingService;

  public List<Song> findAll() {
    return repository.findAll();
  }

  public List<Song> findAll(int limit) {
    return repository.findAll(PageRequest.of(0, limit)).toList();
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

  public List<Song> findByTitleContains(String val, int limit) {
    return repository.findByTitleContainingIgnoreCase(val, PageRequest.of(0, limit)).toList();
  }

  public List<Song> findByLyricsContains(String val) {
    return repository.findByLyricsContainingIgnoreCase(val);
  }

  public List<Song> findByLyricsContains(String val, int limit) {
    return repository.findByLyricsContainingIgnoreCase(val, PageRequest.of(0, limit)).toList();
  }

  public List<Song> findByAuthorId(Long authorId) {
    return repository.findByAuthorId(authorId);
  }

  public List<Song> findByAuthorId(Long authorId, int limit) {
    return repository.findByAuthorId(authorId, PageRequest.of(0, limit)).toList();
  }

  public List<Song> findByCategoryId(Long id) {
    return repository.findByCategoryId(id);
  }

  public List<Song> findByCategoryId(Long id, int limit) {
    return repository.findByCategoryId(id, PageRequest.of(0, limit)).toList();
  }

  public List<Song> findByTagId(Long id) {
    return repository.findByTagsId(id);
  }

  public List<Song> findByRating(Double val) {
    return repository.findByRatings(val);
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
    var song = findById(id);
    song.getCoauthors().forEach(it -> coauthorService.delete(it));
    song.getPlaylists().forEach(it -> it.removeSong(song));
    song.getUsersSongs().forEach(it -> it.removeSong(song));
    song.getRatings().forEach(it -> ratingService.delete(it));
    song.getTags().forEach(song::removeTag);
    song.removeCategory();
    repository.deleteById(id);
  }

  public Song createAndSaveSong(@Valid CreateSongDTO obj) {
    Song song = new Song();
    song.setId(Constants.DEFAULT_ID);
    song.setCategory(categoryService.findById(obj.getCategoryId()));
    song.setUsersSongs(new HashSet<>());
    song.setRatings(new HashSet<>());
    song.setPlaylists(new HashSet<>());
    song.setCreationTime(LocalDateTime.now());
    song.setCoauthors(new HashSet<>());
    song.setTags(new HashSet<>());
    song.setTitle(obj.getTitle());
    song.setLyrics(obj.getLyrics());
    song.setGuitarTabs(obj.getGuitarTabs());
    song.setCurio(obj.getCurio());

    Set<Tag> tags = new HashSet<>();
    obj.getTags().forEach(it -> tags.add(tagService.findOrCreateTag(it)));
    Author author = authorService.findOrCreateAuthor(obj.getAuthorName());

    author.addSong(song);
    tags.forEach(song::addTag);
    var savedSong = repository.save(song);

    obj.getCoauthors().forEach(coauthorDTO -> {
      var auth = authorService.findOrCreateAuthor(coauthorDTO.getAuthorName());
      coauthorService.findOrCreate(savedSong, auth, coauthorDTO.getFunction());
    });
    return savedSong;
  }

  public CreateSongDTO readSongFromFile(String fileName) throws IOException {
    return new ObjectMapper().readValue(
        storageService.getLocation().resolve(fileName).toUri().toURL(), CreateSongDTO.class);
  }
}

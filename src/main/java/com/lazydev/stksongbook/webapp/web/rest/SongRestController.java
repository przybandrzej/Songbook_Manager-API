package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.service.SongCoauthorService;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.TagService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/songs")
@AllArgsConstructor
public class SongRestController {

  private SongService service;
  private SongMapper mapper;
  private UserSongRatingMapper userSongRatingMapper;
  private UserMapper userMapper;
  private PlaylistMapper playlistMapper;
  private AuthorService authorService;
  private SongCoauthorService coauthorService;
  private TagService tagService;

  @GetMapping
  public ResponseEntity<List<SongDTO>> getAll() {
    List<SongDTO> list = service.findAll().stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<SongDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/title/{title}")
  public ResponseEntity<List<SongDTO>> getByTitle(@PathVariable("title") String title) {
    List<SongDTO> list = service.findByTitleContains(title).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/lyrics_fragment/{value}")
  public ResponseEntity<List<SongDTO>> getByLyricsFragment(@PathVariable("value") String value) {
    List<SongDTO> list = service.findByLyricsContains(value).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<SongDTO>> getByCategory(@PathVariable("categoryId") Long id) {
    List<SongDTO> list = service.findByCategoryId(id).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/tag/{tagId}")
  public ResponseEntity<List<SongDTO>> getByTag(@PathVariable("tagId") Long id) {
    List<SongDTO> list = service.findByTagId(id).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}/ratings")
  public ResponseEntity<List<UserSongRatingDTO>> getSongRatings(@PathVariable("id") Long id) {
    List<UserSongRatingDTO> list = service.findById(id).getRatings()
        .stream().map(userSongRatingMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}/users")
  public ResponseEntity<List<UserDTO>> getSongUserLibraries(@PathVariable("id") Long id) {
    List<UserDTO> list = service.findById(id).getUsersSongs()
        .stream().map(userMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}/playlists")
  public ResponseEntity<List<PlaylistDTO>> getSongPlaylists(@PathVariable("id") Long id) {
    List<PlaylistDTO> list = service.findById(id).getPlaylists()
        .stream().map(playlistMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<SongDTO> create(@RequestBody CreateSongDTO obj) {

    var authorOpt = authorService.findByNameNoException(obj.getAuthorName());
    Author author = null;
    if(authorOpt.isPresent()) {
      author = authorOpt.get();
    } else {
      var newAuthor = new Author(Constants.DEFAULT_ID, obj.getAuthorName(), null, null, new HashSet<>(), new HashSet<>());
      author = authorService.save(newAuthor);
    }

    Set<Tag> tags = new HashSet<>();
    obj.getTags().forEach(it -> {
      var tag = tagService.findByNameNoException(it);
      if(tag.isPresent()) {
        tags.add(tag.get());
      } else {
        Tag newTag = new Tag(Constants.DEFAULT_ID, it, new HashSet<>());
        tags.add(tagService.save(newTag));
      }
    });

    var song = mapper.map(obj);
    author.addSong(song);
    song.setTags(tags);
    var savedOnce = service.save(song);

    obj.getCoauthors().forEach(coauthorDTO -> {
      var opt = authorService.findByNameNoException(coauthorDTO.getAuthorName());
      Author auth = null;
      if(opt.isPresent()) {
        auth = opt.get();
      } else {
        var newAuthor = new Author(Constants.DEFAULT_ID, coauthorDTO.getAuthorName(), null, null, new HashSet<>(), new HashSet<>());
        auth = authorService.save(newAuthor);
      }
      var coauthor = new SongCoauthor();
      coauthor.setId(new SongsCoauthorsKey());
      coauthor.setAuthor(auth);
      coauthor.setSong(savedOnce);
      coauthor.setFunction(coauthorDTO.getFunction());
      coauthorService.save(coauthor);
    });
    var completeSong = service.save(savedOnce);
    return new ResponseEntity<>(mapper.map(completeSong), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<SongDTO> update(@RequestBody SongDTO obj) {
    if(service.findByIdNoException(obj.getId()).isEmpty()) {
      throw new EntityNotFoundException(Song.class, obj.getId());
    }
    var song = mapper.map(obj);
    var saved = service.save(song);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

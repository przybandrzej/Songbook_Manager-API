package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.FileSystemStorageService;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ParameterNotDefinedException;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/songs")
@AllArgsConstructor
public class SongResource {

  private final Logger log = LoggerFactory.getLogger(SongResource.class);

  private final SongService service;
  private final SongMapper mapper;
  private final UserSongRatingMapper userSongRatingMapper;
  private final UserMapper userMapper;
  private final PlaylistMapper playlistMapper;
  private final FileSystemStorageService storageService;

  @GetMapping
  public ResponseEntity<List<SongDTO>> getAll(@RequestParam(value = "limit", required = false) Integer limit,
                                              @RequestParam(value = "include_awaiting", required = false) Boolean includeAwaiting) {
    log.debug("Request to get all songs");
    List<SongDTO> list = service.findAll(false, includeAwaiting, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<SongDTO> getById(@PathVariable("id") Long id) {
    log.debug("Request to get song {}", id);
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/latest")
  public ResponseEntity<List<SongDTO>> getLatest(@RequestParam(value = "limit") Integer limit) {
    log.debug("Request to get latest songs");
    List<SongDTO> list = service.findLatestLimited(limit, false).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/title/{title}")
  public ResponseEntity<List<SongDTO>> getByTitleFragment(@PathVariable("title") String title,
                                                          @RequestParam(value = "limit", required = false) Integer limit) {
    log.debug("Request to get all songs by title fragment {}", title);
    List<SongDTO> list = service.findByTitleContains(title, false, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/lyrics_fragment/{value}")
  public ResponseEntity<List<SongDTO>> getByLyricsFragment(@PathVariable("value") String value,
                                                           @RequestParam(value = "limit", required = false) Integer limit) {
    log.debug("Request to get all songs by lyrics fragment {}", value);
    List<SongDTO> list = service.findByLyricsContains(value, false, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<SongDTO>> getByCategory(@PathVariable("categoryId") Long id,
                                                     @RequestParam(value = "limit", required = false) Integer limit) {
    log.debug("Request to get all songs by category {}", id);
    List<SongDTO> list = service.findByCategoryId(id, false, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/author/{authorId}")
  public ResponseEntity<List<SongDTO>> getByAuthor(@PathVariable("authorId") Long id,
                                                   @RequestParam(value = "limit", required = false) Integer limit) {
    log.debug("Request to get all songs by author {}", id);
    List<SongDTO> list = service.findByAuthorId(id, false, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/tag/{tagId}")
  public ResponseEntity<List<SongDTO>> getByTag(@PathVariable("tagId") Long id,
                                                @RequestParam(value = "limit", required = false) Integer limit) {
    log.debug("Request to get all songs by tag {}", id);
    List<SongDTO> list = service.findByTagId(id, false, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/rating")
  public ResponseEntity<List<SongDTO>> getByRating(
      @RequestParam(value = "greaterThanEqual", required = false) Double greaterValue,
      @RequestParam(value = "lessThanEqual", required = false) Double lessValue,
      @RequestParam(value = "equal", required = false) Double value) {
    log.debug("Request to get all songs by rating");
    List<Song> list;
    if(greaterValue != null) {
      list = service.findByRatingEqualGreater(greaterValue);
    } else if(lessValue != null) {
      list = service.findByRatingEqualLess(lessValue);
    } else if(value != null) {
      list = service.findByRating(value);
    } else {
      throw new ParameterNotDefinedException("rating");
    }
    List<SongDTO> dtos = list.stream()
        .map(mapper::map)
        .collect(Collectors.toList());
    return new ResponseEntity<>(dtos, HttpStatus.OK);
  }

  @GetMapping("/id/{id}/ratings")
  public ResponseEntity<List<UserSongRatingDTO>> getSongRatings(@PathVariable("id") Long id) {
    log.debug("Request to get song {} ratings", id);
    List<UserSongRatingDTO> list = service.findById(id).getRatings()
        .stream().map(userSongRatingMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * NOTE! This method is considered obsolete
   */
  @GetMapping("/id/{id}/users")
  public ResponseEntity<List<UserDTO>> getSongUserLibraries(@PathVariable("id") Long id) {
    List<UserDTO> list = service.findById(id).getUsersSongs()
        .stream().map(userMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * NOTE! This method is considered obsolete
   */
  @GetMapping("/id/{id}/playlists")
  public ResponseEntity<List<PlaylistDTO>> getSongPlaylists(@PathVariable("id") Long id) {
    List<PlaylistDTO> list = service.findById(id).getPlaylists()
        .stream().map(playlistMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<SongDTO> create(@RequestBody @Valid CreateSongDTO obj) {
    log.debug("Request to create song {} - {}", obj.getAuthorName(), obj.getTitle());
    var completeSong = service.createAndSaveSong(obj);
    return new ResponseEntity<>(mapper.map(completeSong), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<SongDTO> update(@RequestBody @Valid SongDTO obj) {
    log.debug("Request to update song {}", obj.getId());
    Optional<Song> optional = service.findByIdNoException(obj.getId());
    if(optional.isEmpty()) {
      throw new EntityNotFoundException(Song.class, obj.getId());
    }
    var song = mapper.map(obj);
    var saved = service.updateSong(song);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    log.debug("Request to delete song {}", id);
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/upload")
  public ResponseEntity<SongDTO> loadFromFile(@RequestParam("file") MultipartFile file) throws IOException {
    String name = storageService.store(file);
    CreateSongDTO dto = service.readSongFromFile(name);
    Song song = service.createAndSaveSong(dto);
    return new ResponseEntity<>(mapper.map(song), HttpStatus.OK);
  }

  @PutMapping("/{id}/approve")
  public ResponseEntity<SongDTO> approveSong(@PathVariable Long id) {
    log.debug("Request to approve song {}", id);
    Optional<Song> optional = service.findByIdNoException(id);
    if(optional.isEmpty()) {
      throw new EntityNotFoundException(Song.class, id);
    }
    return new ResponseEntity<>(mapper.map(service.approveSong(optional.get())), HttpStatus.OK);
  }

  /**
   * Get all songs in user's library
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<List<SongDTO>> getUserSongs(@PathVariable("id") Long id) {
    List<SongDTO> list = service.findByUser(id).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * Get all songs edited by user
   */
  @GetMapping("/user/{id}/edited")
  public ResponseEntity<List<SongDTO>> getSongsEditedByUser(@PathVariable("id") Long id) {
    List<SongDTO> list = service.findAddedByUser(id).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  /**
   * Get all songs added by user
   */
  @GetMapping("/user/{id}/added")
  public ResponseEntity<List<SongDTO>> getSongsAddedByUser(@PathVariable("id") Long id) {
    List<SongDTO> list = service.findEditedByUser(id).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PatchMapping("{id}/add-tag")
  public ResponseEntity<SongDTO> addTagToSong(@PathVariable Long id, @RequestBody @Valid UniversalCreateDTO tag) {
    log.debug("Request to add tag {} to song {}", tag.getName(), id);
    var song = service.addTag(id, tag);
    return ResponseEntity.ok(mapper.map(song));
  }

  @PatchMapping("{id}/add-tag-bulk")
  public ResponseEntity<SongDTO> addTagsToSongBulk(@PathVariable Long id, @RequestBody @Valid UniversalCreateDTO[] tags) {
    log.debug("Request to add tag {} to song {}", Arrays.stream(tags).map(UniversalCreateDTO::getName).collect(Collectors.joining(",")), id);
    Song song = service.addTags(id, tags);
    return ResponseEntity.ok(mapper.map(song));
  }

  @PatchMapping("{id}/remove-tag/{tagId}")
  public ResponseEntity<SongDTO> removeTagFromSong(@PathVariable Long id, @PathVariable Long tagId) {
    log.debug("Request to remove tag {} from song {}", tagId, id);
    var song = service.removeTag(id, tagId);
    return ResponseEntity.ok(mapper.map(song));
  }

  @PatchMapping("{id}/remove-tag-bulk/{tagIds}")
  public ResponseEntity<SongDTO> removeTagsFromSongBulk(@PathVariable Long id, @PathVariable Long[] tagIds) {
    log.debug("Request to remove tags {} from song {}", tagIds, id);
    var song = service.removeTags(id, tagIds);
    return ResponseEntity.ok(mapper.map(song));
  }
}

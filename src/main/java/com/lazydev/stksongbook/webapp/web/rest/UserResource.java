package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.SongEdit;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.*;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.mappers.*;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserResource {

  private final Logger log = LoggerFactory.getLogger(UserResource.class);

  private final UserMapper mapper;
  private final UserService service;
  private final UserSongRatingMapper userSongRatingMapper;
  private final PlaylistMapper playlistMapper;
  private final SongMapper songMapper;
  private final SongAddMapper songAddMapper;
  private final SongEditMapper songEditMapper;

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAll(@RequestParam(value = "limit", required = false) Integer limit) {
    if(limit != null) {
      List<UserDTO> list = service.findLimited(limit).stream().map(mapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
    List<UserDTO> list = service.findAll().stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/{id}/ratings")
  public ResponseEntity<List<UserSongRatingDTO>> getRatingsByUserId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<UserSongRatingDTO> list = tmp.getUserRatings()
        .stream().map(userSongRatingMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/ratings/{songId}")
  public ResponseEntity<UserSongRatingDTO> getRatingsOfSongByUserId(@PathVariable("id") Long id, @PathVariable("songId") Long songId) {
    return new ResponseEntity<>(userSongRatingMapper.map(service.findRatingOfSong(id, songId)), HttpStatus.OK);
  }

  @GetMapping("/{id}/playlists")
  public ResponseEntity<List<PlaylistDTO>> getPlaylistsByUserId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<PlaylistDTO> list = tmp.getPlaylists().stream().map(playlistMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/songs")
  public ResponseEntity<List<SongDTO>> getSongsByUserId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<SongDTO> list = tmp.getSongs().stream().map(songMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/added-songs")
  public ResponseEntity<List<SongAddDTO>> getAddedSongsByUserId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<SongAddDTO> list = tmp.getAddedSongs().stream().map(songAddMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/edited-songs")
  public ResponseEntity<List<SongEditDTO>> getEditedSongsByUserId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<SongEditDTO> list = tmp.getEditedSongs().stream().map(songEditMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping
  public ResponseEntity<UserDTO> updateUser(@RequestBody @Valid UserDTO userDTO) {
    User user = service.updateUser(userDTO);
    return ResponseEntity.ok(mapper.map(user));
  }

  @PatchMapping("/{id}/add-song/{songId}")
  public ResponseEntity<Void> addSongToLibrary(@PathVariable Long id, @PathVariable Long songId) {
    log.debug("Add song {} to user {} library", songId, id);
    service.addSongToLibrary(id, songId);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/remove-song/{songId}")
  public ResponseEntity<Void> removeSongFromLibrary(@PathVariable Long id, @PathVariable Long songId) {
    log.debug("Remove song {} from user {} library", songId, id);
    service.removeSongFromLibrary(id, songId);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/add-playlist")
  public ResponseEntity<Void> addPlaylist(@PathVariable Long id, @RequestBody CreatePlaylistDTO playlistDTO) {
    log.debug("Add playlist {} to user {}", playlistDTO, id);
    service.addPlaylist(id, playlistDTO);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/remove-playlist/{playlistId}")
  public ResponseEntity<Void> removePlaylist(@PathVariable Long id, @PathVariable Long playlistId) {
    log.debug("Remove playlist {} from user {}", playlistId, id);
    service.removePlaylist(id, playlistId);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/add-rating")
  public ResponseEntity<Void> addRating(@PathVariable Long id, @RequestBody UserSongRatingDTO ratingDTO) {
    log.debug("Add rating {} to user {}", ratingDTO, id);
    service.addRating(id, ratingDTO);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}/remove-rating/{ratingId}")
  public ResponseEntity<Void> removeRating(@PathVariable Long id, @PathVariable Long ratingId) {
    log.debug("Remove rating {} from user {}", ratingId, id);
    service.removeRating(id, ratingId);
    return ResponseEntity.noContent().build();
  }
}

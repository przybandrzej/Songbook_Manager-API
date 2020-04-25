package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/songs")
@AllArgsConstructor
public class SongRestController {

  private SongService service;
  private SongMapper songMapper;
  private UserSongRatingMapper userSongRatingMapper;
  private UserMapper userMapper;
  private PlaylistMapper playlistMapper;

  @GetMapping
  public List<SongDTO> getAll() {
    return service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/id/{id}")
  public SongDTO getById(@PathVariable("id") Long id) {
    return service.findById(id).map(this::convertToDto).orElse(null);
  }

  @GetMapping("/title/{title}")
  public List<SongDTO> getByTitle(@PathVariable("title") String title) {
    return service.findByTitle(title).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/author/{authorId}")
  public List<SongDTO> getByAuthor(@PathVariable("authorId") Long id) {
    return service.findByAuthorId(id).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/category/{categoryId}")
  public List<SongDTO> getByCategory(@PathVariable("categoryId") Long id) {
    return service.findByCategoryId(id).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/tag/{tagId}")
  public List<SongDTO> getByTag(@PathVariable("tagId") Long id) {
    return service.findByTagId(id).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/id/{id}/ratings")
  public Set<UserSongRatingDTO> getSongRatings(@PathVariable("id") Long id) {
    return service.findById(id)
        .map(song -> song.getRatings()
            .stream().map(userSongRatingMapper::usersSongsRatingsEntityToUserSongRatingDTO).collect(Collectors.toSet()))
        .orElse(null);
  }

  @GetMapping("/id/{id}/users")
  public Set<UserDTO> getSongUserLibraries(@PathVariable("id") Long id) {
    return service.findById(id)
        .map(song -> song.getUsersSongs()
            .stream().map(userMapper::userToUserDTO).collect(Collectors.toSet()))
        .orElse(null);
  }

  @GetMapping("/id/{id}/playlists")
  public Set<PlaylistDTO> getSongPlaylists(@PathVariable("id") Long id) {
    return service.findById(id)
        .map(song -> song.getPlaylists()
            .stream().map(playlistMapper::playlistToPlaylistDTO).collect(Collectors.toSet()))
        .orElse(null);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public SongDTO create(@RequestBody SongDTO obj) {
    return convertToDto(service.save(convertToEntity(obj)));
  }

  @PutMapping
  @ResponseStatus(HttpStatus.OK)
  public void update(@RequestBody SongDTO obj) {
    service.save(convertToEntity(obj));
  }

  @DeleteMapping("/id/{id}")
  public void delete(@PathVariable("id") Long id) {
    service.deleteById(id);
  }

  public SongDTO convertToDto(Song song) {
    return songMapper.songToSongDTO(song);
  }

  public Song convertToEntity(SongDTO songDto) {
    return songMapper.songDTOToSong(songDto);
  }
}

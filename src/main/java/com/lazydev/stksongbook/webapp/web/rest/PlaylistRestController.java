package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/playlists")
@AllArgsConstructor
public class PlaylistRestController {

  private PlaylistService service;
  private PlaylistMapper mapper;

  @GetMapping
  public ResponseEntity<List<PlaylistDTO>> getAll() {
    List<PlaylistDTO> list = service.findAll().stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<PlaylistDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<PlaylistDTO>> getByName(@PathVariable("name") String name) {
    List<PlaylistDTO> list = service.findByName(name).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/ownerId/{id}")
  public ResponseEntity<List<PlaylistDTO>> getByOwnerId(@PathVariable("id") Long ownerId) {
    List<PlaylistDTO> list = service.findByOwnerId(ownerId).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PlaylistDTO> create(@RequestBody CreatePlaylistDTO dto) {
    var playlist = mapper.map(dto);
    playlist.setId(Constants.DEFAULT_ID);
    var saved = service.save(playlist);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<PlaylistDTO> update(@RequestBody PlaylistDTO dto) {
    if(service.findByIdNoException(dto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Playlist.class, dto.getId());
    }
    var playlist = mapper.map(dto);
    var saved = service.save(playlist);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import com.lazydev.stksongbook.webapp.util.PdfMaker;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/playlists")
@AllArgsConstructor
public class PlaylistRestController {

  private PlaylistService service;
  private PlaylistMapper mapper;
  private PdfMaker pdfMaker;

  @GetMapping
  public ResponseEntity<List<PlaylistDTO>> getAll(
          @RequestParam(value = "include_private", required = false, defaultValue = "false") boolean includePrivate) {
      List<PlaylistDTO> list = service.findAll(includePrivate).stream().map(mapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<PlaylistDTO> getById(@PathVariable("id") Long id,
                                             @RequestParam(value = "include_private",
                                                     required = false, defaultValue = "false") boolean includePrivate) throws IOException, URISyntaxException {
    var found = service.findById(id, includePrivate);
    pdfMaker.createPdfFromPlaylist(found);
    return new ResponseEntity<>(mapper.map(found), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<PlaylistDTO>> getByName(@PathVariable("name") String name,
                                                     @RequestParam(value = "include_private", required = false,
                                                             defaultValue = "false") boolean includePrivate) {
    List<PlaylistDTO> list = service.findByName(name, includePrivate).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/ownerId/{id}")
  public ResponseEntity<List<PlaylistDTO>> getByOwnerId(@PathVariable("id") Long ownerId,
                                                        @RequestParam(value = "include_private", required = false,
                                                                defaultValue = "false") boolean includePrivate) {
    List<PlaylistDTO> list = service.findByOwnerId(ownerId, includePrivate).stream().map(mapper::map).collect(Collectors.toList());
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
    if(service.findByIdNoException(dto.getId(), true).isEmpty()) {
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

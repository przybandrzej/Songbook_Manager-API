package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.service.FileSystemStorageService;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import com.lazydev.stksongbook.webapp.util.PdfMaker;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
  private PdfMaker pdfMaker;
  private FileSystemStorageService storageService;

  @GetMapping
  public ResponseEntity<List<PlaylistDTO>> getAll(
          @RequestParam(value = "include_private", required = false, defaultValue = "false") boolean includePrivate) {
      List<PlaylistDTO> list = service.findAll(includePrivate).stream().map(mapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<PlaylistDTO> getById(@PathVariable("id") Long id,
                                             @RequestParam(value = "include_private",
                                                     required = false, defaultValue = "false") boolean includePrivate) {
    var found = service.findById(id, includePrivate);
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

  @GetMapping("/download/{id}")
  public ResponseEntity<Resource> downloadPlaylistPdfSongbook(@PathVariable("id") Long id) {
    var playlist = service.findById(id, true);
    String fileName = pdfMaker.createPdfFromPlaylist(playlist);
    Resource resource = storageService.loadAsResource(fileName);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }
}

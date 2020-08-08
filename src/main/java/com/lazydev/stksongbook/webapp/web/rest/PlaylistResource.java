package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.FileSystemStorageService;
import com.lazydev.stksongbook.webapp.service.PdfService;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/playlists")
@AllArgsConstructor
public class PlaylistResource {

  private final PlaylistService service;
  private final PlaylistMapper mapper;
  private final PdfService pdfService;
  private final FileSystemStorageService storageService;

  @GetMapping
  public ResponseEntity<List<PlaylistDTO>> getAll(
      @RequestParam(value = "include_private", required = false, defaultValue = "false") boolean includePrivate,
      @RequestParam(value = "limit", required = false) Integer limit) {
    if(limit != null) {
      List<PlaylistDTO> list = service.findAll(includePrivate, limit).stream().map(mapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
    List<PlaylistDTO> list = service.findAll(includePrivate).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<PlaylistDTO> getById(@PathVariable("id") Long id) {
    var found = service.findById(id);
    return new ResponseEntity<>(mapper.map(found), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<PlaylistDTO>> getByName(@PathVariable("name") String name,
                                                     @RequestParam(value = "include_private", required = false,
                                                         defaultValue = "false") boolean includePrivate,
                                                     @RequestParam(value = "limit", required = false) Integer limit) {
    if(limit != null) {
      List<PlaylistDTO> list = service.findByNameFragment(name, includePrivate, limit).stream().map(mapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
    List<PlaylistDTO> list = service.findByNameFragment(name, includePrivate).stream().map(mapper::map).collect(Collectors.toList());
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
  public ResponseEntity<PlaylistDTO> create(@RequestBody @Valid CreatePlaylistDTO dto) {
    var saved = service.createPlaylist(dto);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<PlaylistDTO> update(@RequestBody @Valid PlaylistDTO dto) {
    var found = service.findById(dto.getId()); // this is here for EntityNotFound check
    var playlist = mapper.map(dto);
    var saved = service.update(playlist);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/download/{id}")
  public ResponseEntity<Resource> downloadPlaylistPdfSongbook(@PathVariable("id") Long id) throws IOException {
    var playlist = service.findById(id);
    String fileName = pdfService.createPdfFromPlaylist(playlist);
    Resource resource = storageService.loadAsResource(fileName);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"")
        .body(resource);
  }
}

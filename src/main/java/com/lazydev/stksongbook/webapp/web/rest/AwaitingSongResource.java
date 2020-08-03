package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/awaiting-songs")
@AllArgsConstructor
public class AwaitingSongResource {

  private SongService service;
  private SongMapper mapper;

  @GetMapping
  public ResponseEntity<List<SongDTO>> getAll(@RequestParam(value = "limit", required = false) Integer limit) {
    List<SongDTO> list = service.findAll(true, null, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/latest")
  public ResponseEntity<List<SongDTO>> getLatest(@RequestParam(value = "limit") Integer limit) {
    List<SongDTO> list = service.findLatestLimited(limit, true).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<SongDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findByIdAwaiting(id)), HttpStatus.OK);
  }

  @GetMapping("/title/{title}")
  public ResponseEntity<List<SongDTO>> getByTitleFragment(@PathVariable("title") String title,
                                                          @RequestParam(value = "limit", required = false) Integer limit) {
    List<SongDTO> list = service.findByTitleContains(title, true, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/lyrics_fragment/{value}")
  public ResponseEntity<List<SongDTO>> getByLyricsFragment(@PathVariable("value") String value,
                                                           @RequestParam(value = "limit", required = false) Integer limit) {
    List<SongDTO> list = service.findByLyricsContains(value, true, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/category/{categoryId}")
  public ResponseEntity<List<SongDTO>> getByCategory(@PathVariable("categoryId") Long id,
                                                     @RequestParam(value = "limit", required = false) Integer limit) {
    List<SongDTO> list = service.findByCategoryId(id, true, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/tag/{tagId}")
  public ResponseEntity<List<SongDTO>> getByTag(@PathVariable("tagId") Long id,
                                                @RequestParam(value = "limit", required = false) Integer limit) {
    List<SongDTO> list = service.findByTagId(id, true, limit).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }
}

package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.service.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.AuthorMapper;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorResource {

  private final Logger log = LoggerFactory.getLogger(AuthorResource.class);

  private final AuthorService service;
  private final AuthorMapper mapper;
  private final SongMapper songMapper;

  @GetMapping
  public ResponseEntity<List<AuthorDTO>> getAllAuthors(@RequestParam(value = "limit", required = false) Integer limit) {
    if(limit != null) {
      List<AuthorDTO> list = service.findAll(limit).stream().map(mapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
    List<AuthorDTO> list = service.findAll().stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<AuthorDTO>> getAuthorByNameFragment(@PathVariable("name") String name,
                                                           @RequestParam(value = "limit", required = false) Integer limit) {
    if(limit != null) {
      List<AuthorDTO> list = service.findByNameFragment(name, limit).stream().map(mapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
    List<AuthorDTO> list = service.findByNameFragment(name).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/songs")
  public ResponseEntity<List<SongDTO>> getSongsByAuthorId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<SongDTO> list = tmp.getSongs().stream().map(songMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<AuthorDTO> createAuthor(@RequestBody @Valid UniversalCreateDTO authorDto) {
    Author saved = service.create(authorDto);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<AuthorDTO> updateAuthor(@RequestBody @Valid AuthorDTO authorDto) {
    Author saved = service.update(authorDto);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

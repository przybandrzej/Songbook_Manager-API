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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorResource {

  private AuthorService service;
  private AuthorMapper mapper;
  private SongMapper songMapper;

  @GetMapping
  public ResponseEntity<List<AuthorDTO>> getAll(@RequestParam(value = "limit", required = false) Integer limit) {
    if(limit != null) {
      List<AuthorDTO> list = service.findAll(limit).stream().map(mapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
    List<AuthorDTO> list = service.findAll().stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}")
  public ResponseEntity<AuthorDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(mapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<AuthorDTO>> getByNameFragment(@PathVariable("name") String name,
                                                           @RequestParam(value = "limit", required = false) Integer limit) {
    if(limit != null) {
      List<AuthorDTO> list = service.findByNameFragment(name, limit).stream().map(mapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
    List<AuthorDTO> list = service.findByNameFragment(name).stream().map(mapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/id/{id}/songs")
  public ResponseEntity<List<SongDTO>> getSongsByAuthorId(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<SongDTO> list = tmp.getSongs().stream().map(songMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<AuthorDTO> create(@RequestBody @Valid UniversalCreateDTO authorDto) {
    var optional = service.findByNameNoException(authorDto.getName());
    if(optional.isPresent()) {
      throw new EntityAlreadyExistsException(Author.class.getSimpleName(), optional.get().getId(), optional.get().getName());
    }
    var author = mapper.map(authorDto);
    var saved = service.save(author);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<AuthorDTO> update(@RequestBody @Valid AuthorDTO authorDto) {
    if(service.findByIdNoException(authorDto.getId()).isEmpty()) {
      throw new EntityNotFoundException(Author.class, authorDto.getId());
    }
    var author = mapper.map(authorDto);
    var saved = service.save(author);
    return new ResponseEntity<>(mapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

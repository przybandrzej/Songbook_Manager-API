package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.service.TagService;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.dto.TagDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.service.mappers.TagMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tags")
@AllArgsConstructor
public class TagResource {

  private final TagService service;
  private final TagMapper modelMapper;
  private final SongMapper songMapper;

  @GetMapping
  public ResponseEntity<List<TagDTO>> getAll(@RequestParam(value = "limit", required = false) Integer limit) {
    if(limit != null) {
      List<TagDTO> list = service.findLimited(limit).stream().map(modelMapper::map).collect(Collectors.toList());
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
    List<TagDTO> list = service.findAll().stream().map(modelMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TagDTO> getById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(modelMapper.map(service.findById(id)), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<TagDTO>> getByName(@PathVariable("name") String name) {
    List<TagDTO> list = service.findByNameFragment(name).stream().map(modelMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/{id}/songs")
  public ResponseEntity<List<SongDTO>> getSongsByTag(@PathVariable("id") Long id) {
    var tmp = service.findById(id);
    List<SongDTO> list = tmp.getSongs().stream().map(songMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<TagDTO> createTag(@RequestBody @Valid UniversalCreateDTO tagDto) {
    var saved = service.create(tagDto);
    return new ResponseEntity<>(modelMapper.map(saved), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<TagDTO> updateTag(@RequestBody @Valid TagDTO tagDto) {
    var saved = service.update(tagDto);
    return new ResponseEntity<>(modelMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/id/{id}")
  public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id) {
    service.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

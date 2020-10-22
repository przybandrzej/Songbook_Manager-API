package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.data.model.enumeration.CoauthorFunction;
import com.lazydev.stksongbook.webapp.service.SongCoauthorService;
import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongCoauthorMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/coauthors")
@AllArgsConstructor
public class SongCoauthorResource {

  private final SongCoauthorMapper songCoauthorMapper;
  private final SongCoauthorService songCoauthorService;

  @GetMapping("/{id}")
  public ResponseEntity<SongCoauthorDTO> getCoauthorById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(songCoauthorMapper.map(songCoauthorService.findById(id)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<SongCoauthorDTO>> getAllCoauthors() {
    List<SongCoauthorDTO> list = songCoauthorService.findAll()
        .stream().map(songCoauthorMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/author/{id}")
  public ResponseEntity<List<SongCoauthorDTO>> getCoauthorsByAuthorId(@PathVariable("id") Long id) {
    List<SongCoauthorDTO> list = songCoauthorService.findByAuthorId(id)
        .stream().map(songCoauthorMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/song/{id}")
  public ResponseEntity<List<SongCoauthorDTO>> getCoauthorsBySongId(@PathVariable("id") Long id) {
    List<SongCoauthorDTO> list = songCoauthorService.findBySongId(id)
        .stream().map(songCoauthorMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/function/{function}")
  public ResponseEntity<List<SongCoauthorDTO>> getCoauthorsByFunction(@PathVariable("function") CoauthorFunction function) {
    List<SongCoauthorDTO> list = songCoauthorService.findByFunction(function)
        .stream().map(songCoauthorMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<SongCoauthorDTO> createCoauthor(@RequestBody @Valid SongCoauthorDTO songCoauthorDTO) {
    SongCoauthor created = songCoauthorService.create(songCoauthorDTO);
    return new ResponseEntity<>(songCoauthorMapper.map(created), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<SongCoauthorDTO> updateCoauthor(@RequestBody @Valid SongCoauthorDTO songCoauthorDTO) {
    var saved = songCoauthorService.update(songCoauthorDTO);
    return new ResponseEntity<>(songCoauthorMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCoauthor(@PathVariable Long id) {
    songCoauthorService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}

package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.data.model.enumeration.CoauthorFunction;
import com.lazydev.stksongbook.webapp.service.SongCoauthorService;
import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
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

  @GetMapping("/author/{id}")
  public ResponseEntity<List<SongCoauthorDTO>> getByAuthorId(@PathVariable("id") Long id) {
    List<SongCoauthorDTO> list = songCoauthorService.findByAuthorId(id)
        .stream().map(songCoauthorMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/song/{id}")
  public ResponseEntity<List<SongCoauthorDTO>> getBySongId(@PathVariable("id") Long id) {
    List<SongCoauthorDTO> list = songCoauthorService.findBySongId(id)
        .stream().map(songCoauthorMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @GetMapping("/function/{function}")
  public ResponseEntity<List<SongCoauthorDTO>> getByFunction(@PathVariable("function") CoauthorFunction function) {
    List<SongCoauthorDTO> list = songCoauthorService.findByFunction(function)
        .stream().map(songCoauthorMapper::map).collect(Collectors.toList());
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<SongCoauthorDTO> create(@RequestBody @Valid SongCoauthorDTO songCoauthorDTO) {
    if(songCoauthorService.findBySongIdAndAuthorId(
        songCoauthorDTO.getSongId(), songCoauthorDTO.getAuthorId())
        .stream().anyMatch(it -> it.getCoauthorFunction().equals(songCoauthorDTO.getCoauthorFunction()))) {
      throw new EntityAlreadyExistsException(SongCoauthor.class.getSimpleName());
    }
    SongCoauthor entity = songCoauthorMapper.map(songCoauthorDTO);
    SongCoauthor created = songCoauthorService.save(entity);
    return new ResponseEntity<>(songCoauthorMapper.map(created), HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<SongCoauthorDTO> update(@RequestBody @Valid SongCoauthorDTO songCoauthorDTO) {
    if(songCoauthorService.findBySongIdAndAuthorId(
        songCoauthorDTO.getSongId(), songCoauthorDTO.getAuthorId())
        .stream().anyMatch(it -> it.getCoauthorFunction().equals(songCoauthorDTO.getCoauthorFunction()))) {
      throw new EntityNotFoundException(SongCoauthor.class);
    }
    SongCoauthor author = songCoauthorMapper.map(songCoauthorDTO);
    var saved = songCoauthorService.save(author);
    return new ResponseEntity<>(songCoauthorMapper.map(saved), HttpStatus.OK);
  }

  @DeleteMapping("/{songId}/{authorId}/{function}")
  public ResponseEntity<Void> delete(@PathVariable Long songId, @PathVariable Long authorId, @PathVariable String function) {
    songCoauthorService.delete(songId, authorId, function);
    return ResponseEntity.noContent().build();
  }
}

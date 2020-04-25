package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongCoauthorMapper;
import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.service.SongCoauthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/authors")
@AllArgsConstructor
public class SongCoauthorRestController {

  private SongCoauthorMapper songCoauthorMapper;
  private SongCoauthorService songCoauthorService;

  @GetMapping("/id/{id}/songs")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<SongCoauthorDTO> getAuthorSongs(@PathVariable("id") Long id, HttpServletResponse response) {
    List<SongCoauthor> authors = songCoauthorService.findByAuthorId(id);
    return authors.stream().map(songCoauthorMapper::map).collect(Collectors.toList());
  }

  @GetMapping("/song/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<SongCoauthorDTO> getBySongId(@PathVariable("id") Long id, HttpServletResponse response) {
    List<SongCoauthor> authors = songCoauthorService.findBySongId(id);
    return authors.stream().map(songCoauthorMapper::map).collect(Collectors.toList());
  }

  @GetMapping("/function/{function}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<SongCoauthorDTO> getByFunction(@PathVariable("function") String function, HttpServletResponse response) {
    List<SongCoauthor> authors = songCoauthorService.findByFunction(function);
    return authors.stream().map(songCoauthorMapper::map).collect(Collectors.toList());
  }

  @PostMapping("/functions")
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  public SongCoauthorDTO create(@RequestBody SongCoauthorDTO songCoauthorDTO, HttpServletResponse response) {
    //Preconditions.checkNotNull(authorDto);
    SongCoauthor entity = songCoauthorMapper.map(songCoauthorDTO);
    SongCoauthor created = songCoauthorService.save(entity);

    //eventPublisher.publishEvent(new ResourceCreated(this, response, created.getId()));
    return songCoauthorMapper.map(created);
  }

  @PutMapping("/functions")
  @ResponseStatus(HttpStatus.OK)
  public void update(@RequestBody SongCoauthorDTO songCoauthorDTO) {
    SongCoauthor author = songCoauthorMapper.map(songCoauthorDTO);
    songCoauthorService.save(author);
  }

  @DeleteMapping("/functions")
    public void delete(@RequestBody SongCoauthorDTO songCoauthorDTO) {
      var entity = songCoauthorMapper.map(songCoauthorDTO);
      songCoauthorService.delete(entity);
    }
}

package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.SongDTO;
import com.lazydev.stksongbook.webapp.api.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.data.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
@AllArgsConstructor
public class ExternalSongRestController {

  private SongService songService;
  private SongMapper songMapper;

  @GetMapping("/categories/id/{id}/songs")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<SongDTO> getByCategoryId(@PathVariable("id") Long id) {
    return songService.findByCategoryId(id).stream().map(songMapper::songToSongDTO).collect(Collectors.toList());
  }

  @GetMapping("/tags/id/{id}/songs")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<SongDTO> getByTagId(@PathVariable("id") Long id) {
    return songService.findByTagId(id).stream().map(songMapper::songToSongDTO).collect(Collectors.toList());
  }
}

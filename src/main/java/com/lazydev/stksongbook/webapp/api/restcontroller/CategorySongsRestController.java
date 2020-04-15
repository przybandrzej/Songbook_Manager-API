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
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategorySongsRestController {

  private SongService songService;
  private SongMapper songMapper;

  @GetMapping("/id/{id}/songs")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<SongDTO> getById(@PathVariable("id") Long id) {
    return songService.findByCategoryId(id).stream().map(songMapper::songToSongDTO).collect(Collectors.toList());
  }
}

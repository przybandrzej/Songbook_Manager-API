package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.api.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.service.PlaylistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/playlists")
@AllArgsConstructor
public class PlaylistRestController {

  private PlaylistService service;
  private PlaylistMapper modelMapper;

  @GetMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<PlaylistDTO> getAll() {
    return service.findAllPublic().stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/id/{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public PlaylistDTO getById(@PathVariable("id") Long id) {
    return service.findPublicById(id).map(this::convertToDto).orElse(null);
  }

  @GetMapping("/name/{name}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<PlaylistDTO> getByName(@PathVariable("name") String name) {
    return service.findPublicByName(name).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @GetMapping("/ownerId/{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public List<PlaylistDTO> getByOwnerId(@PathVariable("id") Long ownerId) {
    return service.findByOwnerId(ownerId).stream().map(this::convertToDto).collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PlaylistDTO create(@RequestBody PlaylistDTO playlist) {
    return convertToDto(service.save(convertToEntity(playlist)));
  }

  @PutMapping("/id/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void update(@PathVariable("id") Long id, @RequestBody PlaylistDTO playlist) {
    var entity = convertToEntity(playlist);
    entity.setId(id);
    service.save(entity);
  }

  @DeleteMapping("/id/{id}")
  public void delete(@PathVariable("id") Long id) {
    service.deleteById(id);
  }

  public PlaylistDTO convertToDto(Playlist playlist) {
    return modelMapper.playlistToPlaylistDTO(playlist);
  }

  public Playlist convertToEntity(PlaylistDTO playlistDto) {
    return modelMapper.playlistDTOToPlaylist(playlistDto);
  }
}

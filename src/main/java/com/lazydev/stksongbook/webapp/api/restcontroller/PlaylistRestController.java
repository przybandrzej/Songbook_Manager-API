package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.api.dto.SongDTO;
import com.lazydev.stksongbook.webapp.api.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/playlists")
public class PlaylistRestController {

    private PlaylistService service;
    private PlaylistMapper modelMapper;

    public PlaylistRestController(PlaylistService service, PlaylistMapper mapper) {
        this.service = service;
        this.modelMapper = mapper;
    }

    @GetMapping
    public List<PlaylistDTO> getAll(){
        List<Playlist> list = (List<Playlist>) service.findAll();
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public PlaylistDTO getById(@PathVariable("id") Long id) {
        Optional<Playlist> playlist = service.findById(id);
        return playlist.map(this::convertToDto).orElse(null);
    }

    @GetMapping("/name/{name}")
    public List<PlaylistDTO> getByName(@PathVariable("name") String name){
        List<Playlist> list = (List<Playlist>) service.findByName(name);
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/ownerId/{id}")
    public List<PlaylistDTO> getByOwnerId(@PathVariable("id") Long ownerId) {
        //Todo
        return Collections.emptyList();
    }

    @GetMapping("/id/{id}/songs")
    public List<SongDTO> getPlaylistSongs(@PathVariable("id") Long id) {
        //Todo
        return Collections.emptyList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistDTO create(@RequestBody PlaylistDTO playlist) {
        return convertToDto(service.save(convertToEntity(playlist)));
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody PlaylistDTO playlist) {
        service.save(convertToEntity(playlist));
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

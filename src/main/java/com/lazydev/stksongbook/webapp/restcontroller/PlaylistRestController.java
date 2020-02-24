package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.model.Playlist;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/playlists")
public class PlaylistRestController {

    @Autowired
    private PlaylistService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<PlaylistDTO> getAll(){
        List<Playlist> list = (List<Playlist>) service.findAll();;
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public PlaylistDTO getById(@PathVariable("id") Long id) {
        Optional<Playlist> playlist = service.findById(id);;
        if(playlist.isPresent()) return convertToDto(playlist.get());
        else return null;
    }

    @GetMapping("/name/{name}")
    public List<PlaylistDTO> getByName(@PathVariable("name") String name){
        List<Playlist> list = (List<Playlist>) service.findByName(name);
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /*@GetMapping("/ownerId/{id}")
    public Iterable<Playlist> getByOwnerId(@PathVariable("id") Long ownerId) {
        return service.findPublicByOwnerId(ownerId);
    }*/

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlaylistDTO create(@RequestBody Playlist playlist) {
        return service.save(playlist);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Playlist updatePlaylist(@RequestBody Playlist playlist) {
        return service.save(playlist);
    }*/

    @DeleteMapping("/id/{id}")
    public void deletePlaylist(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    public PlaylistDTO convertToDto(Playlist playlist) {
        /*PropertyMap<Playlist, PlaylistDTO> personMap = new PropertyMap<Playlist, PlaylistDTO>() {
            protected void configure() {
                map().setPlaylistRole(source.getPlaylistRole().getName());
                if (playlist.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (playlist.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        PlaylistDTO playlistDto = modelMapper.map(playlist, PlaylistDTO.class);
        return playlistDto;
    }

    //TODO
    public Playlist convertToEntity(PlaylistDTO playlistDto) {
        /*PropertyMap<PlaylistDTO, Playlist> personMap = new PropertyMap<PlaylistDTO, Playlist>() {
            protected void configure() {
                map().setPlaylistRole(source.getPlaylistRole());
                if (playlist.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (playlist.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        Playlist playlist = modelMapper.map(playlistDto, Playlist.class);
        return playlist;
    }
}

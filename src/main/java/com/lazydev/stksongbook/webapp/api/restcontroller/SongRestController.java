package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.api.dto.SongDTO;
import com.lazydev.stksongbook.webapp.api.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.api.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/songs")
public class SongRestController {

    private SongService service;
    private SongMapper songMapper;

    public SongRestController(SongService service, SongMapper mapper) {
        this.service = service;
        this.songMapper = mapper;
    }

    @GetMapping
    public List<SongDTO> getAll(){
        List<Song> list = (List<Song>) service.findAll();
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public SongDTO getById(@PathVariable("id") Long id) {
        Optional<Song> song = service.findById(id);
        return song.map(this::convertToDto).orElse(null);
    }

    @GetMapping("/title/{title}")
    public List<SongDTO> getByTitle(@PathVariable("title") String title) {
        //Todo
        return Collections.emptyList();
    }

    @GetMapping("/author/{authorId}")
    public List<SongDTO> getByAuthor(@PathVariable("authorId") Long id) {
        //Todo
        return Collections.emptyList();
    }

    @GetMapping("/category/{categoryId}")
    public List<SongDTO> getByTitle(@PathVariable("categoryId") Long id) {
        //Todo
        return Collections.emptyList();
    }

    @GetMapping("/id/{id}/ratings")
    public List<UserSongRatingDTO> getSongRatings(@PathVariable("id") Long id) {
        //Todo
        return Collections.emptyList();
    }

    @GetMapping("/id/{id}/user_libs")
    public List<User> getSongLibraries(@PathVariable("id") Long id) {
        //Todo
        return Collections.emptyList();
    }

    @GetMapping("/id/{id}/playlists")
    public List<PlaylistDTO> getSongPlaylists(@PathVariable("id") Long id) {
        //Todo
        return Collections.emptyList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SongDTO create(@RequestBody SongDTO obj) {
        return convertToDto(service.save(convertToEntity(obj)));
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody SongDTO obj) {
        service.save(convertToEntity(obj));
    }

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    public SongDTO convertToDto(Song song) {
        return songMapper.songToSongDTO(song);
    }

    public Song convertToEntity(SongDTO songDto) {
        return songMapper.songDTOToSong(songDto);
    }
}

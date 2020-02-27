package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.SongDTO;
import com.lazydev.stksongbook.webapp.dto.SongMapper;
import com.lazydev.stksongbook.webapp.model.Song;
import com.lazydev.stksongbook.webapp.service.SongService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/songs")
public class SongRestController {

    @Autowired
    private SongService service;

    @Autowired
    private SongMapper songMapper;

    @GetMapping
    public List<SongDTO> getAll(){
        List<Song> list = (List<Song>) service.findAll();;
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public SongDTO getById(@PathVariable("id") Long id) {
        Optional<Song> song = service.findById(id);;
        return song.map(this::convertToDto).orElse(null);
    }

    /*@GetMapping("/title/{title}")
    public Iterable<Song> getByTitle(@PathVariable("title") String title){
        return service.findByTitle(title);
    }

    @GetMapping("/author/{authorId}")
    public Iterable<Song> getByTitle(@PathVariable("authorId") Long authorId){
        return service.findByAuthorId(authorId);
    }

    @GetMapping("/category/{categoryId}")
    public Iterable<Song> getByCategory(@PathVariable("categoryId") Long categoryId){
        return service.findByCategoryId(categoryId);
    }*/

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

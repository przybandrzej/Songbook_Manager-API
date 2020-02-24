package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.SongDTO;
import com.lazydev.stksongbook.webapp.model.Song;
import com.lazydev.stksongbook.webapp.service.SongService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ModelMapper modelMapper;

    @GetMapping
    public List<SongDTO> getAll(){
        List<Song> list = (List<Song>) service.findAll();;
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public SongDTO getById(@PathVariable("id") Long id) {
        Optional<Song> song = service.findById(id);;
        if(song.isPresent()) return convertToDto(song.get());
        else return null;
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

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Song addSong(@RequestBody Song obj) {
        return service.save(obj);
    }

    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Song updateSong(@RequestBody Song obj) {
        return service.save(obj);
    }*/

    @DeleteMapping("/id/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    public SongDTO convertToDto(Song song) {
        /*PropertyMap<Song, SongDTO> personMap = new PropertyMap<Song, SongDTO>() {
            protected void configure() {
                map().setSongRole(source.getSongRole().getName());
                if (song.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (song.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        SongDTO songDto = modelMapper.map(song, SongDTO.class);
        return songDto;
    }

    //TODO
    public Song convertToEntity(SongDTO songDto) {
        /*PropertyMap<SongDTO, Song> personMap = new PropertyMap<SongDTO, Song>() {
            protected void configure() {
                map().setSongRole(source.getSongRole());
                if (song.getFirstName() != null) { map().setFirstName(source.getFirstName()); }
                else { map().setFirstName(""); }
                if (song.getLastName() != null) { map().setLastName(source.getLastName()); }
                else { map().setLastName(""); }
            }
        };
        modelMapper.addMappings(personMap);*/
        Song song = modelMapper.map(songDto, Song.class);
        return song;
    }
}

package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.model.Song;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/songs")
public class SongRestController {

    @Autowired
    private SongService manager;

    @GetMapping("/get")
    public Iterable<Song> getAll(){
        return manager.findAll();
    }

    @GetMapping("/get/id/{id}")
    public Optional<Song> getById(@PathVariable("id") Long id) {
        return manager.findById(id);
    }

    @GetMapping("/get/title/{title}")
    public Iterable<Song> getByTitle(@PathVariable("title") String title){
        return manager.findByTitle(title);
    }

    @GetMapping("/get/author/{authorId}")
    public Iterable<Song> getByTitle(@PathVariable("authorId") Long authorId){
        return manager.findByAuthorId(authorId);
    }

    @GetMapping("/get/category/{categoryId}")
    public Iterable<Song> getByCategory(@PathVariable("categoryId") Long categoryId){
        return manager.findByCategoryId(categoryId);
    }

    @PostMapping   // Add mapping?
    //@ResponseStatus(HttpStatus.CREATED)
    public Song addSong(@RequestBody Song obj) {
        return manager.save(obj);
    }

    @PutMapping   // Add mapping?
    //@ResponseStatus(HttpStatus.OK)
    public Song updateSong(@RequestBody Song obj) {
        return manager.save(obj);
    }

    @DeleteMapping   // Add mapping?
    public void deleteSong(@RequestParam Long id) {
        manager.deleteById(id);
    }
}

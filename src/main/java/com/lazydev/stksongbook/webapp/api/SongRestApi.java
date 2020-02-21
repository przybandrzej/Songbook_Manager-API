package com.lazydev.stksongbook.webapp.api;

import com.lazydev.stksongbook.webapp.manager.SongManager;
import com.lazydev.stksongbook.webapp.model.Song;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/songs")
public class SongRestApi {

    @Autowired
    private SongManager manager;

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

    @PostMapping   // Add mapping?
    public Song addSong(@RequestBody Song obj) {
        return manager.save(obj);
    }

    @PutMapping   // Add mapping?
    public Song updateSong(@RequestBody Song obj) {
        return manager.save(obj);
    }

    @DeleteMapping   // Add mapping?
    public void deleteSong(@RequestParam Long id) {
        manager.deleteById(id);
    }
}

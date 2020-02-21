package com.lazydev.stksongbook.webapp.api;

import com.lazydev.stksongbook.webapp.manager.PlaylistManager;
import com.lazydev.stksongbook.webapp.model.Playlist;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/playlists")
public class PlaylistRestApi {

    @Autowired
    private PlaylistManager manager;

    @GetMapping("/get")
    public Iterable<Playlist> getAll(){
        return manager.findAllPublic();
    }

    @GetMapping("/get/id/{id}")
    public Optional<Playlist> getById(@PathVariable("id") Long id) {
        return manager.findPublicById(id);
    }

    @GetMapping("/get/name/{name}")
    public Iterable<Playlist> getByName(@PathVariable("name") String name){
        return manager.findPublicByName(name);
    }

    @GetMapping("/get/owner/{id}")
    public Iterable<Playlist> getByOwnerId(@PathVariable("id") Long ownerId){
        return manager.findPublicByOwnerId(ownerId);
    }

    @PostMapping   // Add mapping?
    public Playlist addPlaylist(@RequestBody Playlist playlist) {
        return manager.save(playlist);
    }

    @PutMapping   // Add mapping?
    public Playlist updatePlaylist(@RequestBody Playlist playlist) {
        return manager.save(playlist);
    }

    @DeleteMapping   // Add mapping?
    public void deletePlaylist(@RequestParam Long id) {
        manager.deleteById(id);
    }
}

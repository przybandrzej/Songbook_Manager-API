package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.repository.PlaylistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlaylistService {

    private PlaylistRepository repository;

    public Optional<Playlist> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Playlist> findPublicById(Long id) {
        return repository.findPublicById(id);
    }

    public List<Playlist> findByName(String name) {
        return repository.findAllByName(name);
    }

    public List<Playlist> findPublicByName(String name) {
        return repository.findPublicByName(name);
    }

    public List<Playlist> findByOwnerId(Long id) {
        return repository.findByOwner(id);
    }

    public List<Playlist> findPublicByOwnerId(Long id) {
        return repository.findPublicByOwner(id);
    }

    public List<Playlist> findAllPublic() {
        return repository.findAllPublic();
    }

    public List<Playlist> findAll() {
        return repository.findAll();
    }

    public Playlist save(Playlist saveAuthor) {
        return repository.save(saveAuthor);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}

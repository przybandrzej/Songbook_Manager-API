package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.repository.PlaylistRepository;
import com.lazydev.stksongbook.webapp.data.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class PlaylistService {

    private PlaylistRepository dao;

    public Optional<Playlist> findById(Long id) {
        return dao.findById(id);
    }

    public Optional<Playlist> findPublicById(Long id) {
        return dao.findPublicById(id);
    }

    public List<Playlist> findByName(String name) {
        return dao.findAllByName(name);
    }

    public List<Playlist> findPublicByName(String name) {
        return dao.findPublicByName(name);
    }

    public List<Playlist> findByOwnerId(Long id) {
        return dao.findByOwner(id);
    }

    public List<Playlist> findPublicByOwnerId(Long id) {
        return dao.findPublicByOwner(id);
    }

    public List<Playlist> findAllPublic() {
        return dao.findAllPublic();
    }

    public List<Playlist> findAll() {
        return dao.findAll();
    }

    public Playlist save(Playlist saveAuthor) {
        return dao.save(saveAuthor);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}

package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.repository.SongRepository;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SongService {

    private SongRepository dao;

    public Optional<Song> findById(Long id) {
        return dao.findById(id);
    }

    public List<Song> findByTitle(String name) {
        List<Song> list = new ArrayList<>();
        for (Song element : dao.findAll()) {
            if(element.getTitle().equals(name)) list.add(element);
        }
        return list;
    }

    public List<Song> findByAuthorId(Long authorId) {
        return dao.findByAuthorId(authorId);
    }

    public List<Song> findByCategoryId(Long id) {
        return dao.findByCategoryId(id);
    }

    public List<Song> findByTagId(Long id) {
        return dao.findByTagId(id);
    }

    public List<Song> findAll() {
        return dao.findAll();
    }

    public Song save(Song saveSong) {
        return dao.save(saveSong);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}

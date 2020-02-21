package com.lazydev.stksongbook.webapp.manager;

import com.lazydev.stksongbook.webapp.dao.SongDAO;
import com.lazydev.stksongbook.webapp.model.Song;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SongManager {

    @Autowired
    private SongDAO dao;

    public Optional<Song> findById(Long id) {
        return dao.findById(id);
    }

    public Iterable<Song> findByTitle(String name) {
        List<Song> list = new ArrayList<>();
        for (Song element : dao.findAll()) {
            if(element.getTitle().equals(name)) list.add(element);
        }
        return list;
    }

    public Iterable<Song> findByAuthorId(Long authorId) {
        List<Song> list = new ArrayList<>();
        for (Song element : dao.findAll()) {
            if(element.getAuthorId() == authorId) list.add(element);
        }
        return list;
    }

    public Iterable<Song> findByCategoryId(Long id) {
        List<Song> list = new ArrayList<>();
        for (Song element : dao.findAll()) {
            if(element.getCategoryId() == id) list.add(element);
        }
        return list;
    }

    // TODO addtags to songs
    // TODO Add searching by multiple tags
    /*public Iterable<Song> findByTag(String name) {
        List<Song> list = new ArrayList<>();
        for (Song element : dao.findAll()) {
            if(element.getTitle().equals(name)) list.add(element);
        }
        return list;
    }*/

    public Iterable<Song> findAll() {
        return dao.findAll();
    }

    public Song save(Song saveSong) {
        return dao.save(saveSong);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}

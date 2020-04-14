package com.lazydev.stksongbook.webapp.data.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.repository.AuthorRepository;
import com.lazydev.stksongbook.webapp.data.repository.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;
    private SongRepository songRepository;

    public Iterable<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    public Iterable<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }

    //TODO
    public Iterable<Song> findAuthorSongs(Long id) {
        return null;
    }

    public Author save(Author saveAuthor) {
        return authorRepository.save(saveAuthor);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}

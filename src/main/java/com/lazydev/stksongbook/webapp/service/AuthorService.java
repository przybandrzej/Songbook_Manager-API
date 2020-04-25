package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.repository.AuthorRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public Optional<Author> findByIdNoException(Long id) {
        return authorRepository.findById(id);
    }

    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Author.class, id));
    }

    public List<Author> findByName(String name) {
        return authorRepository.findByNameIgnoreCase(name);
    }

    public Author save(Author saveAuthor) {
        return authorRepository.save(saveAuthor);
    }

    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}

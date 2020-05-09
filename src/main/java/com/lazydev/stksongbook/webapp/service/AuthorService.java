package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.repository.AuthorRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {

  private AuthorRepository authorRepository;

  public List<Author> findAll() {
    return authorRepository.findAll();
  }

  public List<Author> findLimited(int limit) {
    return authorRepository.findAll(PageRequest.of(0, limit)).toList();
  }

  public Optional<Author> findByIdNoException(Long id) {
    return authorRepository.findById(id);
  }

  public Author findById(Long id) {
    return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Author.class, id));
  }

  public List<Author> findByNameFragment(String name) {
    return authorRepository.findByNameContainingIgnoreCase(name);
  }

  public Optional<Author> findByNameNoException(String name) {
    return authorRepository.findByName(name);
  }

  public Author findByName(String name) {
    return authorRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(Author.class, name));
  }

  public Author save(Author saveAuthor) {
    return authorRepository.save(saveAuthor);
  }

  public void deleteById(Long id) {
    authorRepository.deleteById(id);
  }

  public Author findOrCreateAuthor(String name) {
    var opt = authorRepository.findByName(name);
    Author auth = null;
    if (opt.isPresent()) {
      auth = opt.get();
    } else {
      var newAuthor = new Author(Constants.DEFAULT_ID, name,
          null, null, new HashSet<>(), new HashSet<>());
      auth = authorRepository.save(newAuthor);
    }
    return auth;
  }
}

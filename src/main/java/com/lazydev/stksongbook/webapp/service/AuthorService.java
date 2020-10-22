package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.AuthorRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.CannotDeleteEntityException;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional
public class AuthorService {

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;
  @Value("${spring.flyway.placeholders.role.moderator}")
  private String moderatorRoleName;

  private final AuthorRepository authorRepository;
  private final UserContextService userContextService;

  public AuthorService(AuthorRepository authorRepository, UserContextService userContextService) {
    this.authorRepository = authorRepository;
    this.userContextService = userContextService;
  }

  public List<Author> findAll() {
    return authorRepository.findAll();
  }

  public List<Author> findAll(int limit) {
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

  public List<Author> findByNameFragment(String name, int limit) {
    return authorRepository.findByNameContainingIgnoreCase(name, PageRequest.of(0, limit)).toList();
  }

  public Optional<Author> findByNameNoException(String name) {
    return authorRepository.findByName(name);
  }

  public Author findByName(String name) {
    return authorRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(Author.class, name));
  }

  public void deleteById(Long id) {
    User user = userContextService.getCurrentUser();
    if(!(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName)
        || user.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Forbidden operation.");
    }
    var author = findById(id);
    if(!author.getSongs().isEmpty()) {
      throw new CannotDeleteEntityException(Author.class.getSimpleName(), "There are songs belonging to this author.");
    }
    if(!author.getCoauthorSongs().isEmpty()) {
      throw new CannotDeleteEntityException(Author.class.getSimpleName(), "There are songs that this author is a coauthor of.");
    }
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

  public Author update(@Valid AuthorDTO authorDto) {
    User user = userContextService.getCurrentUser();
    if(!(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName)
        || user.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Forbidden operation.");
    }
    Author author = findById(authorDto.getId());
    author.setName(authorDto.getName());
    author.setBiographyUrl(authorDto.getBiographyUrl());
    author.setPhotoResource(authorDto.getPhotoResource());
    return authorRepository.save(author);
  }

  public Author create(@Valid UniversalCreateDTO authorDto) {
    User user = userContextService.getCurrentUser();
    if(!(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName)
        || user.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Forbidden operation.");
    }
    if(findByNameNoException(authorDto.getName()).isPresent()) {
      throw new EntityAlreadyExistsException(Author.class.getSimpleName(), authorDto.getName());
    }
    Author author = new Author();
    author.setId(Constants.DEFAULT_ID);
    author.setName(authorDto.getName());
    return authorRepository.save(author);
  }
}

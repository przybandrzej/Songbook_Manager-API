package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.enumeration.CoauthorFunction;
import com.lazydev.stksongbook.webapp.repository.AuthorRepository;
import com.lazydev.stksongbook.webapp.repository.SongCoauthorRepository;
import com.lazydev.stksongbook.webapp.repository.SongRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class SongCoauthorService {

  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;
  @Value("${spring.flyway.placeholders.role.moderator}")
  private String moderatorRoleName;

  private final SongCoauthorRepository repository;
  private final SongRepository songRepository;
  private final AuthorRepository authorRepository;
  private final UserContextService userContextService;

  public SongCoauthorService(SongCoauthorRepository repository, SongRepository songRepository, AuthorRepository authorRepository, UserContextService userContextService) {
    this.repository = repository;
    this.songRepository = songRepository;
    this.authorRepository = authorRepository;
    this.userContextService = userContextService;
  }

  public Optional<SongCoauthor> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public SongCoauthor findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(SongCoauthor.class, id));
  }

  public List<SongCoauthor> findBySongIdAndAuthorId(Long songId, Long authorId) {
    return repository.findBySongIdAndAuthorId(songId, authorId);
  }

  public SongCoauthor findBySongIdAndAuthorIdAndFunction(Long songId, Long authorId, CoauthorFunction function) {
    return repository.findBySongIdAndAuthorIdAndCoauthorFunction(songId, authorId, function)
        .orElseThrow(() -> new EntityNotFoundException(SongCoauthor.class));
  }

  public Optional<SongCoauthor> findBySongIdAndAuthorIdAndFunctionNoException(Long songId, Long authorId, CoauthorFunction function) {
    return repository.findBySongIdAndAuthorIdAndCoauthorFunction(songId, authorId, function);
  }

  public List<SongCoauthor> findAll() {
    return repository.findAll();
  }

  public List<SongCoauthor> findBySongId(Long songId) {
    return repository.findBySongId(songId);
  }

  public List<SongCoauthor> findByAuthorId(Long id) {
    return repository.findByAuthorId(id);
  }

  public List<SongCoauthor> findByFunction(CoauthorFunction function) {
    return repository.findByCoauthorFunction(function);
  }

  public void deleteById(Long id) {
    SongCoauthor coauthor = findById(id);
    User currentUser = userContextService.getCurrentUser();
    if(!coauthor.getSong().isAwaiting()
        && !(currentUser.getUserRole().getName().equals(superuserRoleName)
        || currentUser.getUserRole().getName().equals(adminRoleName)
        || currentUser.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Approved song can be updated only by a moderator or admin.");
    }
    coauthor.getAuthor().removeCoauthor(coauthor);
    coauthor.getSong().removeCoauthor(coauthor);
    repository.delete(coauthor);
  }

  public SongCoauthor findOrCreate(Song song, Author author, CoauthorFunction function) {
    Optional<SongCoauthor> opt = findBySongIdAndAuthorIdAndFunctionNoException(song.getId(), author.getId(), function);
    if(opt.isPresent()) {
      return opt.get();
    }
    var coauthor = new SongCoauthor();
    coauthor.setId(Constants.DEFAULT_ID);
    author.addCoauthorSong(coauthor);
    song.addCoauthor(coauthor);
    coauthor.setCoauthorFunction(function);
    return repository.save(coauthor);
  }

  public void deleteAll(Collection<SongCoauthor> list) {
    repository.deleteAll(list);
  }

  public SongCoauthor create(@Valid CreateCoauthorDTO coauthorDTO, Song song) {
    Author author = authorRepository.findByName(coauthorDTO.getAuthorName()).orElseThrow(() -> new EntityNotFoundException(Author.class, coauthorDTO.getAuthorName()));
    if(findBySongIdAndAuthorIdAndFunctionNoException(song.getId(), author.getId(), coauthorDTO.getCoauthorFunction()).isPresent()) {
      throw new EntityAlreadyExistsException(SongCoauthor.class.getSimpleName());
    }
    User currentUser = userContextService.getCurrentUser();
    if(!song.isAwaiting()
        && !(currentUser.getUserRole().getName().equals(superuserRoleName)
        || currentUser.getUserRole().getName().equals(adminRoleName)
        || currentUser.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Approved song can be updated only by a moderator or admin.");
    }
    SongCoauthor coauthor = new SongCoauthor();
    coauthor.setId(Constants.DEFAULT_ID);
    author.addCoauthorSong(coauthor);
    song.addCoauthor(coauthor);
    coauthor.setCoauthorFunction(coauthorDTO.getCoauthorFunction());
    return repository.save(coauthor);
  }

  public SongCoauthor create(@Valid SongCoauthorDTO songCoauthorDTO) {
    if(findBySongIdAndAuthorIdAndFunctionNoException(songCoauthorDTO.getSongId(), songCoauthorDTO.getAuthorId(), songCoauthorDTO.getCoauthorFunction()).isPresent()) {
      throw new EntityAlreadyExistsException(SongCoauthor.class.getSimpleName());
    }
    User currentUser = userContextService.getCurrentUser();
    Author author = authorRepository.findById(songCoauthorDTO.getAuthorId()).orElseThrow(() -> new EntityNotFoundException(Author.class, songCoauthorDTO.getAuthorId()));
    Song song = songRepository.findById(songCoauthorDTO.getSongId()).orElseThrow(() -> new EntityNotFoundException(Song.class, songCoauthorDTO.getSongId()));
    if(!song.isAwaiting()
        && !(currentUser.getUserRole().getName().equals(superuserRoleName)
        || currentUser.getUserRole().getName().equals(adminRoleName)
        || currentUser.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Approved song can be updated only by a moderator or admin.");
    }
    SongCoauthor coauthor = new SongCoauthor();
    coauthor.setId(Constants.DEFAULT_ID);
    author.addCoauthorSong(coauthor);
    song.addCoauthor(coauthor);
    coauthor.setCoauthorFunction(songCoauthorDTO.getCoauthorFunction());
    return repository.save(coauthor);
  }

  public SongCoauthor update(@Valid SongCoauthorDTO coauthorDTO) {
    SongCoauthor coauthor = findById(coauthorDTO.getId());
    Song song = coauthor.getSong();
    User currentUser = userContextService.getCurrentUser();
    if(!song.isAwaiting()
        && !(currentUser.getUserRole().getName().equals(superuserRoleName)
        || currentUser.getUserRole().getName().equals(adminRoleName)
        || currentUser.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Approved song can be updated only by a moderator or admin.");
    }
    Author newAuthor = authorRepository.findById(coauthorDTO.getAuthorId()).orElseThrow(() -> new EntityNotFoundException(Author.class, coauthorDTO.getAuthorId()));
    Song newSong = songRepository.findById(coauthorDTO.getSongId()).orElseThrow(() -> new EntityNotFoundException(Song.class, coauthorDTO.getSongId()));
    newAuthor.addCoauthorSong(coauthor);
    newSong.addCoauthor(coauthor);
    coauthor.setCoauthorFunction(coauthorDTO.getCoauthorFunction());
    return repository.save(coauthor);
  }
}

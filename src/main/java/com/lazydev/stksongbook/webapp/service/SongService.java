package com.lazydev.stksongbook.webapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.repository.SongAddRepository;
import com.lazydev.stksongbook.webapp.repository.SongEditRepository;
import com.lazydev.stksongbook.webapp.repository.SongRepository;
import com.lazydev.stksongbook.webapp.repository.UserSongRatingRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateVerseDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.exception.ForbiddenOperationException;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
@Validated
public class SongService {

  private final Logger log = LoggerFactory.getLogger(SongService.class);

  private final SongRepository repository;
  private final TagService tagService;
  private final AuthorService authorService;
  private final SongCoauthorService coauthorService;
  private final CategoryService categoryService;
  private final FileSystemStorageService storageService;
  private final UserSongRatingRepository ratingRepository;
  private final UserContextService userContextService;
  private final SongAddRepository songAddRepository;
  private final SongEditRepository songEditRepository;
  private final VerseService verseService;
  @Value("${spring.flyway.placeholders.role.superuser}")
  private String superuserRoleName;
  @Value("${spring.flyway.placeholders.role.admin}")
  private String adminRoleName;
  @Value("${spring.flyway.placeholders.role.moderator}")
  private String moderatorRoleName;

  public SongService(SongRepository repository, TagService tagService, AuthorService authorService, SongCoauthorService coauthorService, CategoryService categoryService, FileSystemStorageService storageService, UserSongRatingRepository ratingRepository, UserContextService userContextService, SongAddRepository songAddRepository, SongEditRepository songEditRepository, VerseService verseService) {
    this.repository = repository;
    this.tagService = tagService;
    this.authorService = authorService;
    this.coauthorService = coauthorService;
    this.categoryService = categoryService;
    this.storageService = storageService;
    this.ratingRepository = ratingRepository;
    this.userContextService = userContextService;
    this.songAddRepository = songAddRepository;
    this.songEditRepository = songEditRepository;
    this.verseService = verseService;
  }

  public List<Song> findAll(Boolean awaiting, Boolean includeAwaiting, Integer limit) {
    log.debug("Include awaiting songs {} and limit result to {}", includeAwaiting, limit);
    if(limit != null) {
      if(includeAwaiting != null) {
        if(Boolean.TRUE.equals(includeAwaiting)) {
          return findAll(limit);
        } else {
          return repository.findByIsAwaiting(false);
        }
      } else if(awaiting != null) {
        return findAll(limit, awaiting);
      } else {
        return findAll(limit, false);
      }
    } else {
      if(includeAwaiting != null) {
        return repository.findAll();
      } else if(awaiting != null) {
        return findAll(awaiting);
      } else {
        return findAll(false);
      }
    }
  }

  private List<Song> findAll(boolean awaiting) {
    return repository.findByIsAwaiting(awaiting);
  }

  private List<Song> findAll(int limit, boolean awaiting) {
    return repository.findByIsAwaiting(awaiting, PageRequest.of(0, limit)).toList();
  }

  private List<Song> findAll(int limit) {
    return repository.findAll(PageRequest.of(0, limit)).toList();
  }

  public Optional<Song> findByIdNoException(Long id) {
    return repository.findById(id);
  }

  public Song findById(Long id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(Song.class, id));
  }

  public Optional<Song> findByIdAwaitingNoException(Long id) {
    return repository.findByIdAndIsAwaiting(id, true);
  }

  public Song findByIdAwaiting(Long id) {
    return repository.findByIdAndIsAwaiting(id, true).orElseThrow(() -> new EntityNotFoundException(Song.class, id));
  }

  public List<Song> findByTitle(String val, Boolean awaiting) {
    if(awaiting != null) {
      return repository.findByTitleIgnoreCaseAndIsAwaiting(val, awaiting);
    } else {
      return repository.findByTitleIgnoreCase(val);
    }
  }

  public List<Song> findByTitleContains(String val, Boolean awaiting, Integer limit) {
    log.debug("Include awaiting songs {} and limit result to {}", awaiting, limit);
    if(awaiting != null) {
      if(limit != null) {
        return repository.findByTitleContainingIgnoreCaseAndIsAwaiting(val, awaiting, PageRequest.of(0, limit)).toList();
      } else {
        return repository.findByTitleContainingIgnoreCaseAndIsAwaiting(val, awaiting);
      }
    } else {
      if(limit != null) {
        return repository.findByTitleContainingIgnoreCase(val, PageRequest.of(0, limit)).toList();
      } else {
        return repository.findByTitleContainingIgnoreCase(val);
      }
    }
  }

  public List<Song> findByRating(Double val) {
    return repository.findByRatingsAndIsAwaiting(val, false);
  }

  public List<Song> findByRatingEqualGreater(Double val) {
    return repository.findByRatingsRatingGreaterThanEqualAndIsAwaiting(val, false);
  }

  public List<Song> findByRatingEqualLess(Double val) {
    return repository.findByRatingsRatingLessThanEqualAndIsAwaiting(val, false);
  }

  public List<Song> findByCreationTimeEqualGreater(Instant val, Boolean awaiting) {
    if(awaiting != null) {
      return repository.findByAddedTimestampGreaterThanEqualAndIsAwaiting(val, awaiting);
    } else {
      return repository.findByAddedTimestampGreaterThanEqual(val);
    }
  }

  public List<Song> findByCreationTimeEqualLess(Instant val, Boolean awaiting) {
    if(awaiting != null) {
      return repository.findByAddedTimestampLessThanEqualAndIsAwaiting(val, awaiting);
    }
    return repository.findByAddedTimestampLessThanEqual(val);
  }

  public void deleteById(Long id) {
    var song = findById(id);
    User currentUser = userContextService.getCurrentUser();
    filterRequestForApprovedSong(song, currentUser, "deleted");
    filterRequestForAwaitingSong(song, currentUser, "deleted");
    coauthorService.deleteAll(song.getCoauthors());
    song.getPlaylists().forEach(it -> it.removeSong(song));
    song.getUsersSongs().forEach(it -> it.removeSong(song));
    ratingRepository.deleteAll(song.getRatings());
    songAddRepository.delete(song.getAdded());
    songEditRepository.deleteAll(song.getEdits());
    verseService.deleteAll(song.getVerses());
    repository.deleteById(id);
  }

  public Song updateSong(Song song) {
    User currentUser = userContextService.getCurrentUser();
    filterRequestForApprovedSong(song, currentUser, "updated");
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    SongEdit finalEdit = songEditRepository.save(edit);
    if(song.removeEditIf(it -> it.getTimestamp().equals(finalEdit.getTimestamp()))) {
      song.addEdit(finalEdit);
    }
    return repository.save(song);
  }

  public Song createAndSaveSong(@Valid CreateSongDTO obj) {
    Song song = new Song();
    song.setId(Constants.DEFAULT_ID);
    song.setCategory(categoryService.findById(obj.getCategoryId()));
    song.setTitle(obj.getTitle());

    song.setTrivia(obj.getTrivia());
    song.setAwaiting(true);

    Set<Tag> tags = new HashSet<>();
    obj.getTags().forEach(it -> tags.add(tagService.findOrCreateTag(it)));
    Author author = authorService.findOrCreateAuthor(obj.getAuthorName());

    author.addSong(song);
    tags.forEach(song::addTag);
    var savedSong = repository.save(song);

    for(CreateVerseDTO verse : obj.getVerses()) {
      verseService.create(verse, savedSong);
    }

    obj.getCoauthors().forEach(coauthorDTO -> {
      var auth = authorService.findOrCreateAuthor(coauthorDTO.getAuthorName());
      coauthorService.findOrCreate(savedSong, auth, coauthorDTO.getCoauthorFunction());
    });

    SongAdd timestamp = new SongAdd();
    timestamp.setId(Constants.DEFAULT_ID);
    userContextService.getCurrentUser().addAddedSong(timestamp);
    savedSong.setAdded(timestamp);
    songAddRepository.save(timestamp);

    return savedSong;
  }

  public CreateSongDTO readSongFromFile(String fileName) throws IOException {
    return new ObjectMapper().readValue(
        storageService.getLocation().resolve(fileName).toUri().toURL(), CreateSongDTO.class);
  }

  public Song approveSong(Song song) {
    song.setAwaiting(false);
    return repository.save(song);
  }

  public List<Song> findByUser(Long userId) {
    return repository.findByUsersSongsId(userId);
  }

  public List<Song> findEditedByUser(Long userId) {
    return repository.findByAddedAddedById(userId);
  }

  public List<Song> findAddedByUser(Long userId) {
    return repository.findByEditsEditedById(userId);
  }

  public Song addTag(Long songId, UniversalCreateDTO tag) {
    User currentUser = userContextService.getCurrentUser();
    Song song = repository.findById(songId).orElseThrow(() -> new EntityNotFoundException(Song.class, songId));
    filterRequestForApprovedSong(song, currentUser, "updated");
    Tag created = tagService.findOrCreateTag(tag.getName());
    song.addTag(created);
    var saved = repository.save(song);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
    return saved;
  }

  public Song removeTag(Long songId, Long tagId) {
    User currentUser = userContextService.getCurrentUser();
    Song song = repository.findById(songId).orElseThrow(() -> new EntityNotFoundException(Song.class, songId));
    filterRequestForApprovedSong(song, currentUser, "updated");
    Tag tag = tagService.findById(tagId);
    song.removeTag(tag);
    if(tag.getSongs().isEmpty()) {
      tagService.deleteById(tagId);
    }
    var saved = repository.save(song);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
    return saved;
  }

  public Song removeTags(Long songId, Long[] tagIds) {
    User currentUser = userContextService.getCurrentUser();
    Song song = repository.findById(songId).orElseThrow(() -> new EntityNotFoundException(Song.class, songId));
    filterRequestForApprovedSong(song, currentUser, "updated");
    for(Long tagId : tagIds) {
      Tag tag = tagService.findById(tagId);
      song.removeTag(tag);
      if(tag.getSongs().isEmpty()) {
        tagService.deleteById(tagId);
      }
    }
    var saved = repository.save(song);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
    return saved;
  }

  public Song addTags(Long songId, UniversalCreateDTO[] tags) {
    User currentUser = userContextService.getCurrentUser();
    Song song = repository.findById(songId).orElseThrow(() -> new EntityNotFoundException(Song.class, songId));
    filterRequestForApprovedSong(song, currentUser, "updated");
    for(UniversalCreateDTO tag : tags) {
      Tag created = tagService.findOrCreateTag(tag.getName());
      song.addTag(created);
    }
    var saved = repository.save(song);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
    return saved;
  }

  public void addVerse(Long songId, CreateVerseDTO dto) {
    User currentUser = userContextService.getCurrentUser();
    Song song = repository.findById(songId).orElseThrow(() -> new EntityNotFoundException(Song.class, songId));
    filterRequestForApprovedSong(song, currentUser, "updated");
    verseService.create(dto, song);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
  }

  public void removeVerse(Long songId, Long verseId) {
    User currentUser = userContextService.getCurrentUser();
    Song song = repository.findById(songId).orElseThrow(() -> new EntityNotFoundException(Song.class, songId));
    filterRequestForApprovedSong(song, currentUser, "updated");
    verseService.deleteById(verseId);
    SongEdit edit = new SongEdit();
    edit.setId(Constants.DEFAULT_ID);
    currentUser.addEditedSong(edit);
    song.addEdit(edit);
    songEditRepository.save(edit);
  }

  private void filterRequestForApprovedSong(Song song, User user, String option) {
    if(!song.isAwaiting()
        && !(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName)
        || user.getUserRole().getName().equals(moderatorRoleName))) {
      throw new ForbiddenOperationException("Approved song can be " + option + " only by a moderator or admin.");
    }
  }

  private void filterRequestForAwaitingSong(Song song, User user, String option) {
    if(song.isAwaiting() && (!song.getAdded().getAddedBy().getId().equals(userContextService.getCurrentUser().getId())
        && !(user.getUserRole().getName().equals(superuserRoleName)
        || user.getUserRole().getName().equals(adminRoleName)
        || user.getUserRole().getName().equals(moderatorRoleName)))) {
      throw new ForbiddenOperationException("Awaiting song can be " + option + " only by its author, moderator or admin.");
    }
  }
}

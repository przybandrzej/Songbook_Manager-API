package com.lazydev.stksongbook.webapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.SongAdd;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.repository.SongAddRepository;
import com.lazydev.stksongbook.webapp.repository.SongEditRepository;
import com.lazydev.stksongbook.webapp.repository.SongRepository;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
@Validated
public class SongService {

  private SongRepository repository;
  private TagService tagService;
  private AuthorService authorService;
  private SongCoauthorService coauthorService;
  private CategoryService categoryService;
  private FileSystemStorageService storageService;
  private UserSongRatingService ratingService;
  private UserService userService;
  private SongAddRepository songAddRepository;
  private SongEditRepository songEditRepository;

  public List<Song> findAll(Boolean awaiting, Boolean includeAwaiting, Integer limit) {
    if(limit != null) {
      if(includeAwaiting != null) {
        if(includeAwaiting) {
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

  public List<Song> findByLyricsContains(String val, Boolean awaiting, Integer limit) {
    if(awaiting != null) {
      if(limit != null) {
        return repository.findByLyricsContainingIgnoreCaseAndIsAwaiting(val, awaiting, PageRequest.of(0, limit)).toList();
      } else {
        return repository.findByLyricsContainingIgnoreCaseAndIsAwaiting(val, awaiting);
      }
    } else {
      if(limit != null) {
        return repository.findByLyricsContainingIgnoreCase(val, PageRequest.of(0, limit)).toList();
      } else {
        return repository.findByLyricsContainingIgnoreCase(val);
      }
    }
  }

  public List<Song> findByAuthorId(Long authorId, Boolean awaiting, Integer limit) {
    if(awaiting != null) {
      if(limit != null) {
        return repository.findByAuthorIdAndIsAwaiting(authorId, awaiting, PageRequest.of(0, limit)).toList();
      } else {
        return repository.findByAuthorIdAndIsAwaiting(authorId, awaiting);
      }
    } else {
      if(limit != null) {
        return repository.findByAuthorId(authorId, PageRequest.of(0, limit)).toList();
      } else {
        return repository.findByAuthorId(authorId);
      }
    }
  }

  public List<Song> findByCategoryId(Long id, Boolean awaiting, Integer limit) {
    if(awaiting != null) {
      if(limit != null) {
        return repository.findByCategoryIdAndIsAwaiting(id, awaiting, PageRequest.of(0, limit)).toList();
      } else {
        return repository.findByCategoryIdAndIsAwaiting(id, awaiting);
      }
    } else {
      if(limit != null) {
        return repository.findByCategoryId(id, PageRequest.of(0, limit)).toList();
      } else {
        return repository.findByCategoryId(id);
      }
    }
  }

  public List<Song> findByTagId(Long id, Boolean awaiting, Integer limit) {
    if(awaiting != null) {
      if(limit != null) {
        return repository.findByTagsIdAndIsAwaiting(id, awaiting, PageRequest.of(0, limit)).toList();
      } else {
        return repository.findByTagsIdAndIsAwaiting(id, awaiting);
      }
    } else {
      return repository.findByTagsId(id);
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

  public List<Song> findByCreationTimeEqualGreater(LocalDateTime val, Boolean awaiting) {
    if(awaiting != null) {
      return repository.findByAddedTimestampGreaterThanEqualAndIsAwaiting(val, awaiting);
    } else {
      return repository.findByAddedTimestampGreaterThanEqual(val);
    }
  }

  public List<Song> findByCreationTimeEqualLess(LocalDateTime val, Boolean awaiting) {
    if(awaiting != null) {
      return repository.findByAddedTimestampLessThanEqualAndIsAwaiting(val, awaiting);
    }
    return repository.findByAddedTimestampLessThanEqual(val);
  }

  public Song save(Song saveSong) {
    return repository.save(saveSong);
  }

  public void deleteById(Long id) {
    var song = findById(id);
    song.getCoauthors().forEach(it -> coauthorService.delete(it));
    song.getPlaylists().forEach(it -> it.removeSong(song));
    song.getUsersSongs().forEach(it -> it.removeSong(song));
    song.getRatings().forEach(it -> ratingService.delete(it));
    song.getTags().forEach(song::removeTag);
    song.removeCategory();
    songAddRepository.delete(song.getAdded());
    songEditRepository.deleteAll(song.getEdits());
    repository.deleteById(id);
  }

  public Song createAndSaveSong(@Valid CreateSongDTO obj) {
    Song song = new Song();
    song.setId(Constants.DEFAULT_ID);
    song.setCategory(categoryService.findById(obj.getCategoryId()));
    song.setUsersSongs(new HashSet<>());
    song.setRatings(new HashSet<>());
    song.setPlaylists(new HashSet<>());
    song.setCoauthors(new HashSet<>());
    song.setTags(new HashSet<>());
    song.setTitle(obj.getTitle());
    song.setLyrics(obj.getLyrics());
    song.setGuitarTabs(obj.getGuitarTabs());
    song.setTrivia(obj.getTrivia());
    song.setAwaiting(true);

    Set<Tag> tags = new HashSet<>();
    obj.getTags().forEach(it -> tags.add(tagService.findOrCreateTag(it)));
    Author author = authorService.findOrCreateAuthor(obj.getAuthorName());

    author.addSong(song);
    tags.forEach(song::addTag);
    var savedSong = repository.save(song);

    obj.getCoauthors().forEach(coauthorDTO -> {
      var auth = authorService.findOrCreateAuthor(coauthorDTO.getAuthorName());
      coauthorService.findOrCreate(savedSong, auth, coauthorDTO.getCoauthorFunction());
    });

    SongAdd timestamp = new SongAdd();
    timestamp.setId(Constants.DEFAULT_ID);
    timestamp.setTimestamp(LocalDateTime.now());
    userService.findById(obj.getUserIdAdded()).addAddedSong(timestamp);
    savedSong.setAdded(timestamp);
    songAddRepository.save(timestamp);

    return savedSong;
  }

  public CreateSongDTO readSongFromFile(String fileName) throws IOException {
    return new ObjectMapper().readValue(
        storageService.getLocation().resolve(fileName).toUri().toURL(), CreateSongDTO.class);
  }

  public List<Song> findLatestLimited(int limit, Boolean awaiting) {
    String properties = "creationTime";
    if(awaiting != null) {
      return repository.findByIsAwaiting(awaiting, PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, properties))).toList();
    } else {
      return repository.findAll(PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, properties))).toList();
    }
  }
}

package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.repository.SongCoauthorRepository;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SongCoauthorServiceTest {
  /**
   * Tests for all methods might not make sense since most of them just call the JpaRepository.
   * The following tested methods are the only that required tests.
   */

  @Mock
  SongCoauthorRepository repository;

  @InjectMocks
  SongCoauthorService service;

  @Test
  void findByUserIdAndSongIdNoException() {
    SongCoauthor coauthor = getSample(1, 1);
    Mockito.when(repository.findBySongIdAndAuthorId(1L, 1L)).thenReturn(Optional.of(coauthor));
    Mockito.when(repository.findBySongIdAndAuthorId(1L, 2L)).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> service.findBySongIdAndAuthorId(1L, 1L));
    assertNotNull(service.findBySongIdAndAuthorId(1L, 1L));
    assertEquals(coauthor, service.findBySongIdAndAuthorId(1L, 1L));

    assertThrows(EntityNotFoundException.class, () -> service.findBySongIdAndAuthorId(1L, 2L));
  }

  @Test
  void deleteById() {
    var sample = getSample(1, 1);
    Mockito.when(repository.findBySongIdAndAuthorIdAndFunction(1L, 1L, "muzyka"))
        .thenReturn(Optional.of(sample));
    assertDoesNotThrow(() -> service.delete(sample));
  }

  private SongCoauthor getSample(long songId, long authorId) {
    Author author = new Author();
    author.setId(authorId);
    author.setCoauthorSongs(new HashSet<>());
    Song song = new Song();
    song.setId(songId);
    song.setCoauthors(new HashSet<>());
    SongCoauthor coauthor = new SongCoauthor();
    coauthor.setId(new SongsCoauthorsKey());
    coauthor.setSong(song);
    coauthor.setAuthor(author);
    coauthor.setFunction("muzyka");
    return coauthor;
  }
}

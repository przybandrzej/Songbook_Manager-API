package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.repository.AuthorRepository;
import com.lazydev.stksongbook.webapp.service.exception.CannotDeleteEntityException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
  /**
   * Tests for all methods might not make sense since most of them is just a call to the JpaRepository.
   * The following tested methods are the only that required tests.
   */

  @Mock
  AuthorRepository repository;

  @InjectMocks
  AuthorService service = new AuthorService(repository);

  @Test
  void findById() {
    Author author = new Author(1L, "Andrew", null, null, new HashSet<>(), new HashSet<>());
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(author));

    assertDoesNotThrow(() -> service.findById(1L));
    assertNotNull(service.findById(1L));
    assertEquals(author, service.findById(1L));

    assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
  }

  @Test
  void findByName() {
    Author author = new Author(1L, "Andrew", null, null, new HashSet<>(), new HashSet<>());
    Mockito.when(repository.findByName("John")).thenReturn(Optional.empty());
    Mockito.when(repository.findByName("Andrew")).thenReturn(Optional.of(author));

    assertDoesNotThrow(() -> service.findByName("Andrew"));
    assertNotNull(service.findByName("Andrew"));
    assertEquals(author, service.findByName("Andrew"));

    assertThrows(EntityNotFoundException.class, () -> service.findByName("John"));
  }

  @Test
  void findOrCreateAuthor() {
    Author found = new Author(1L, "Andrew", null, null, new HashSet<>(), new HashSet<>());
    Author created = new Author(2L, "John", null, null, new HashSet<>(), new HashSet<>());
    Mockito.when(repository.findByName("John")).thenReturn(Optional.empty());
    Mockito.when(repository.findByName("Andrew")).thenReturn(Optional.of(found));
    Mockito.when(repository.save(new Author(0L, "John",
            null, null, new HashSet<>(), new HashSet<>()))).thenReturn(created);

    assertDoesNotThrow(() -> service.findOrCreateAuthor("Andrew"));
    assertNotNull(service.findOrCreateAuthor("Andrew"));
    assertEquals(found, service.findOrCreateAuthor("Andrew"));

    assertDoesNotThrow(() -> service.findOrCreateAuthor("John"));
    assertNotNull(service.findOrCreateAuthor("John"));
    assertEquals(created, service.findOrCreateAuthor("John"));
  }

  @Test
  void deleteById() {
    Song song = new Song();
    song.setId(1L);
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(new Author(1L, "Andrew", null, null, new HashSet<>(), Set.of(song))));
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(3L)).thenReturn(Optional.of(new Author(3L, "John", null, null, new HashSet<>(), new HashSet<>())));

    assertThrows(CannotDeleteEntityException.class, () -> service.deleteById(1L));
    assertThrows(EntityNotFoundException.class, () -> service.deleteById(2L));
    assertDoesNotThrow(() -> service.deleteById(3L));
  }
}

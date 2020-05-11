package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.repository.AuthorRepository;
import com.lazydev.stksongbook.webapp.service.exception.CannotDeleteEntityException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class AuthorServiceTest {

  @Mock
  AuthorRepository repository;

  @InjectMocks
  AuthorService service;

  @Test
  void findById() {
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(new Author(1L, "Andrew", null, null, new HashSet<>(), new HashSet<>())));

    Author author = service.findById(1L);
    assertNotNull(author);
    assertEquals(1L, author.getId());
    assertEquals("Andrew", author.getName());

    assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
  }

  @Test
  void findByName() {
    Mockito.when(repository.findByName("John")).thenReturn(Optional.empty());
    Mockito.when(repository.findByName("Andrew")).thenReturn(Optional.of(new Author(1L, "Andrew", null, null, new HashSet<>(), new HashSet<>())));

    Author author = service.findByName("Andrew");
    assertNotNull(author);
    assertEquals(1L, author.getId());

    assertThrows(EntityNotFoundException.class, () -> service.findByName("John"));
  }

  @Test
  void findOrCreateAuthor() {
    Mockito.when(repository.findByName("John")).thenReturn(Optional.empty());
    Mockito.when(repository.findByName("Andrew")).thenReturn(Optional.of(new Author(1L, "Andrew", null, null, new HashSet<>(), new HashSet<>())));
    Mockito.when(repository.save(new Author(Constants.DEFAULT_ID, "John",
            null, null, new HashSet<>(), new HashSet<>()))).thenReturn(new Author(2L, "John",
        null, null, new HashSet<>(), new HashSet<>()));

    Author author = service.findByName("Andrew");
    assertNotNull(author);
    assertEquals(1L, author.getId());

    Author author2 = service.findByName("John");
    assertNotNull(author2);
    assertEquals(2L, author2.getId());
    assertEquals("John", author2.getName());
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

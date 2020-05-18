package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.repository.TagRepository;
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
class TagServiceTest {
  /**
   * Tests for all methods might not make sense since most of them just call the JpaRepository.
   * The following tested methods are the only that required tests.
   */

  @Mock
  TagRepository repository;

  @InjectMocks
  TagService service = new TagService(repository);

  @Test
  void findById() {
    Tag cat = new Tag(1L, "tag sample", new HashSet<>());
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(cat));

    assertDoesNotThrow(() -> service.findById(1L));
    assertNotNull(service.findById(1L));
    assertEquals(cat, service.findById(1L));

    assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
  }

  @Test
  void findByName() {
    Tag found = new Tag(1L, "tag sample", new HashSet<>());
    Mockito.when(repository.findByName("John")).thenReturn(Optional.empty());
    Mockito.when(repository.findByName("tag sample")).thenReturn(Optional.of(found));

    assertDoesNotThrow(() -> service.findByName("tag sample"));
    assertNotNull(service.findByName("tag sample"));
    assertEquals(found, service.findByName("tag sample"));

    assertThrows(EntityNotFoundException.class, () -> service.findByName("John"));
  }

  @Test
  void deleteById() {
    Song song = new Song();
    song.setId(1L);
    song.setTags(new HashSet<>());
    Tag tagWithSong = new Tag(1L, "tag", new HashSet<>());
    song.addTag(tagWithSong);
    Tag emptyTag = new Tag(3L, "empty cat", new HashSet<>());

    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(tagWithSong));
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(3L)).thenReturn(Optional.of(emptyTag));

    assertAll(() -> {
      assertDoesNotThrow(() -> service.deleteById(1L));
      assertEquals(0, song.getTags().size());
    });
    assertThrows(EntityNotFoundException.class, () -> service.deleteById(2L));
    assertDoesNotThrow(() -> service.deleteById(3L));
  }

  @Test
  void findOrCreateTag() {
    Tag found = new Tag(1L, "tag1", new HashSet<>());
    Tag created = new Tag(2L, "tag2", new HashSet<>());
    Mockito.when(repository.findByName("tag2")).thenReturn(Optional.empty());
    Mockito.when(repository.findByName("tag1")).thenReturn(Optional.of(found));
    Mockito.when(repository.save(new Tag(0L, "tag2", new HashSet<>()))).thenReturn(created);

    assertDoesNotThrow(() -> service.findOrCreateTag("tag1"));
    assertNotNull(service.findOrCreateTag("tag1"));
    assertEquals(found, service.findOrCreateTag("tag1"));

    assertDoesNotThrow(() -> service.findOrCreateTag("tag2"));
    assertNotNull(service.findOrCreateTag("tag2"));
    assertEquals(created, service.findOrCreateTag("tag2"));
  }
}

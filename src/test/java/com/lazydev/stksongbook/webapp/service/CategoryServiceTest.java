package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.repository.CategoryRepository;
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
class CategoryServiceTest {
  /**
   * Tests for all methods might not make sense since most of them just call the JpaRepository.
   * The following tested methods are the only that required tests.
   */

  @Mock
  CategoryRepository repository;

  @InjectMocks
  CategoryService service = new CategoryService(repository);

  @Test
  void findById() {
    Category cat = new Category(1L, "category sample", new HashSet<>());
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(cat));

    assertDoesNotThrow(() -> service.findById(1L));
    assertNotNull(service.findById(1L));
    assertEquals(cat, service.findById(1L));

    assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
  }

  @Test
  void findByName() {
    Category found = new Category(1L, "category sample", new HashSet<>());
    Mockito.when(repository.findByName("John")).thenReturn(Optional.empty());
    Mockito.when(repository.findByName("Andrew")).thenReturn(Optional.of(found));

    assertDoesNotThrow(() -> service.findByName("Andrew"));
    assertNotNull(service.findByName("Andrew"));
    assertEquals(found, service.findByName("Andrew"));

    assertThrows(EntityNotFoundException.class, () -> service.findByName("John"));
  }

  @Test
  void deleteById() {
    Song song = new Song();
    song.setId(1L);
    Category categoryWithSong = new Category(1L, "category", Set.of(song));
    Category emptyCategory = new Category(3L, "empty cat", new HashSet<>());

    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(categoryWithSong));
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(3L)).thenReturn(Optional.of(emptyCategory));

    assertThrows(CannotDeleteEntityException.class, () -> service.deleteById(1L));
    assertThrows(EntityNotFoundException.class, () -> service.deleteById(2L));
    assertDoesNotThrow(() -> service.deleteById(3L));
  }
}

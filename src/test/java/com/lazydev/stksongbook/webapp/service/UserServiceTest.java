package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.repository.SongTimestampRepository;
import com.lazydev.stksongbook.webapp.repository.UserRepository;
import com.lazydev.stksongbook.webapp.service.exception.UserNotExistsException;
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
class UserServiceTest {
  /**
   * Tests for all methods might not make sense since most of them just call the JpaRepository.
   * The following tested methods are the only that required tests.
   */

  @Mock
  UserRepository repository;

  @Mock
  PlaylistService playlistService;

  @Mock
  SongTimestampRepository timestampRepository;

  @InjectMocks
  UserService service = new UserService(repository, playlistService, timestampRepository);

  @Test
  void findById() {
    UserRole role = new UserRole(1L, "admin", new HashSet<>());
    User user = new User(1L, "u@se.r", "password", "username", role,
        null, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(user));

    assertDoesNotThrow(() -> service.findById(1L));
    assertNotNull(service.findById(1L));
    assertEquals(user, service.findById(1L));

    assertThrows(UserNotExistsException.class, () -> service.findById(2L));
  }

  @Test
  void findByName() {
    UserRole role = new UserRole(1L, "admin", new HashSet<>());
    User user = new User(1L, "u@se.r", "password", "username", role,
        null, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());

    Mockito.when(repository.findByUsername("John")).thenReturn(Optional.empty());
    Mockito.when(repository.findByUsername("username")).thenReturn(Optional.of(user));

    assertDoesNotThrow(() -> service.findByUsername("username"));
    assertNotNull(service.findByUsername("username"));
    assertEquals(user, service.findByUsername("username"));

    assertThrows(UserNotExistsException.class, () -> service.findByUsername("John"));
  }

  @Test
  void findByEmail() {
    UserRole role = new UserRole(1L, "admin", new HashSet<>());
    User user = new User(1L, "u@se.r", "password", "username", role,
        null, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());

    Mockito.when(repository.findByEmail("a@a.pl")).thenReturn(Optional.empty());
    Mockito.when(repository.findByEmail("u@se.r")).thenReturn(Optional.of(user));

    assertDoesNotThrow(() -> service.findByEmail("u@se.r"));
    assertNotNull(service.findByEmail("u@se.r"));
    assertEquals(user, service.findByEmail("u@se.r"));

    assertThrows(UserNotExistsException.class, () -> service.findByEmail("a@a.pl"));
  }

  @Test
  void deleteById() {
    UserRole role = new UserRole(1L, "admin", new HashSet<>());
    User user = new User(1L, "u@se.r", "password", "username", role,
        null, null, new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());


    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(user));
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> service.deleteById(1L));
    assertThrows(UserNotExistsException.class, () -> service.deleteById(2L));
  }
}

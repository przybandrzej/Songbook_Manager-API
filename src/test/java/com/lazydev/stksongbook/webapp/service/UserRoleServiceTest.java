package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.repository.UserRoleRepository;
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
class UserRoleServiceTest {
  /**
   * Tests for all methods might not make sense since most of them just call the JpaRepository.
   * The following tested methods are the only that required tests.
   */

  @Mock
  UserRoleRepository repository;

  @InjectMocks
  UserRoleService service = new UserRoleService(repository);

  @Test
  void findById() {
    UserRole userRole = new UserRole(1L, "userRole sample", new HashSet<>());
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(userRole));

    assertDoesNotThrow(() -> service.findById(1L));
    assertNotNull(service.findById(1L));
    assertEquals(userRole, service.findById(1L));

    assertThrows(EntityNotFoundException.class, () -> service.findById(2L));
  }

  @Test
  void findByName() {
    UserRole found = new UserRole(1L, "userRole sample", new HashSet<>());
    Mockito.when(repository.findByName("John")).thenReturn(Optional.empty());
    Mockito.when(repository.findByName("userRole sample")).thenReturn(Optional.of(found));

    assertDoesNotThrow(() -> service.findByName("userRole sample"));
    assertNotNull(service.findByName("userRole sample"));
    assertEquals(found, service.findByName("userRole sample"));

    assertThrows(EntityNotFoundException.class, () -> service.findByName("John"));
  }

  @Test
  void deleteById() {
    User user = new User();
    user.setId(1L);
    UserRole userRoleWithUser = new UserRole(1L, "userRole", Set.of(user));
    UserRole emptyUserRole = new UserRole(3L, "empty role", new HashSet<>());

    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(userRoleWithUser));
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(3L)).thenReturn(Optional.of(emptyUserRole));

    assertThrows(CannotDeleteEntityException.class, () -> service.deleteById(1L));
    assertThrows(EntityNotFoundException.class, () -> service.deleteById(2L));
    assertDoesNotThrow(() -> service.deleteById(3L));
  }
}

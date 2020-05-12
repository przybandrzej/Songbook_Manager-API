package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.repository.UserSongRatingRepository;
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
class UserSongRatingServiceTest {
  /**
   * Tests for all methods might not make sense since most of them just call the JpaRepository.
   * The following tested methods are the only that required tests.
   */

  @Mock
  UserSongRatingRepository repository;

  @InjectMocks
  UserSongRatingService service = new UserSongRatingService(repository);

  @Test
  void findByUserIdAndSongIdNoException() {
    User user = new User();
    user.setId(1L);
    Song song = new Song();
    song.setId(1L);
    UserSongRating rating = new UserSongRating();
    rating.setSong(song);
    rating.setUser(user);
    rating.setRating(0.9);
    Mockito.when(repository.findByUserIdAndSongId(1L, 1L)).thenReturn(Optional.of(rating));
    Mockito.when(repository.findByUserIdAndSongId(1L, 2L)).thenReturn(Optional.empty());

    assertDoesNotThrow(() -> service.findByUserIdAndSongId(1L, 1L));
    assertNotNull(service.findByUserIdAndSongId(1L, 1L));
    assertEquals(rating, service.findByUserIdAndSongId(1L, 1L));

    assertThrows(EntityNotFoundException.class, () -> service.findByUserIdAndSongId(1L, 2L));
  }

  @Test
  void deleteById() {
    var sample = getSample(1, 1);
    Mockito.when(repository.findByUserIdAndSongId(1L, 1L)).thenReturn(Optional.of(sample));
    assertDoesNotThrow(() -> service.delete(sample));
  }

  private UserSongRating getSample(long songid, long userid) {
    User user = new User();
    user.setId(userid);
    Song song = new Song();
    song.setId(songid);
    UserSongRating rating = new UserSongRating();
    rating.setSong(song);
    rating.setUser(user);
    rating.setRating(0.9);
    return rating;
  }
}

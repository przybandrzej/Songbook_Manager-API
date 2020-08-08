package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.repository.PlaylistRepository;
import com.lazydev.stksongbook.webapp.security.UserContextService;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {
  /**
   * Tests for all methods might not make sense since most of them just call the JpaRepository.
   * The following tested methods are the only that required tests.
   */

  @Mock
  PlaylistRepository repository;
  @Mock
  SongService songService;
  @Mock
  UserContextService userService;

  @InjectMocks
  PlaylistService service = new PlaylistService(repository, songService, userService);

  @Test
  void findById() {
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(getSamplePrivatePlaylist(1L)));

    assertDoesNotThrow(() -> service.findById(1L, true));
    assertNotNull(service.findById(1L, true));
    assertEquals(getSamplePrivatePlaylist(1L), service.findById(1L, true));

    assertThrows(EntityNotFoundException.class, () -> service.findById(2L, true));
  }

  @Test
  void findByNameFragment() {
    Playlist found = getSamplePublicPlaylist(1L);
    Mockito.when(repository.findByNameContainingIgnoreCase("John")).thenReturn(Collections.emptyList());
    Mockito.when(repository.findByNameContainingIgnoreCase("oNe")).thenReturn(List.of(found));

    assertDoesNotThrow(() -> service.findByNameFragment("John", true));
    assertEquals(0, service.findByNameFragment("John", true).size());

    assertDoesNotThrow(() -> service.findByNameFragment("oNe", true));
    assertEquals(1, service.findByNameFragment("oNe", true).size());
    assertEquals(found, service.findByNameFragment("oNe", true).get(0));
  }

  @Test
  void deleteById() {
    Song song = new Song();
    song.setId(1L);
    Playlist playlistWithSong = getSamplePrivatePlaylist(1L);
    playlistWithSong.setSongs(Set.of(song));
    Playlist emptyPlaylist = getSamplePublicPlaylist(3L);

    Mockito.when(repository.findById(1L)).thenReturn(Optional.of(playlistWithSong));
    Mockito.when(repository.findById(2L)).thenReturn(Optional.empty());
    Mockito.when(repository.findById(3L)).thenReturn(Optional.of(emptyPlaylist));

    assertDoesNotThrow(() -> service.deleteById(1L));
    assertThrows(EntityNotFoundException.class, () -> service.deleteById(2L));
    assertDoesNotThrow(() -> service.deleteById(3L));
  }

  private Playlist getSamplePrivatePlaylist(Long id) {
    Playlist playlist = new Playlist();
    playlist.setId(id);
    playlist.setName("testing playlist title");
    User user = new User();
    user.setUsername("playlist owner");
    playlist.setOwner(user);
    playlist.setSongs(new HashSet<>());
    Song song = new Song();
    song.setId(1L);
    song.setTitle("testing song 1 title");
    song.setPlaylists(new HashSet<>());
    Author author = new Author();
    author.setId(1L);
    author.setName("song author");
    song.setAuthor(author);
    song.setLyrics("Lorem ipsum dolor sit amet, \nconsectetur adipiscing elit, \nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua. \nUt enim ad minim veniam, \nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. \nDuis aute irure dolor in reprehenderit \nin voluptate velit esse cillum dolore eu fugiat nulla pariatur. \nExcepteur sint occaecat cupidatat non proident, \nsunt in culpa qui officia deserunt mollit anim id est laborum.");
    song.setGuitarTabs("sample tabs");
    playlist.addSong(song);
    playlist.setPrivate(true);
    return playlist;
  }

  private Playlist getSamplePublicPlaylist(Long id) {
    var playlist = getSamplePrivatePlaylist(id);
    playlist.setPrivate(false);
    playlist.setName("public one");
    return playlist;
  }
}

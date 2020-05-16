package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PdfServiceTest {

  @Mock
  private FileSystemStorageService fileSystemStorageService;
  private PdfService pdfService;

  @BeforeEach
  void setUp() {
    given(fileSystemStorageService.getLocation()).willReturn(Paths.get("./test_storage"));
    pdfService = new PdfService(fileSystemStorageService);
  }

  @Test
  void createPdfFromPlaylist() {
    Playlist playlist = new Playlist();
    playlist.setId(1L);
    playlist.setPrivate(false);
    playlist.setName("Playlist name");
    playlist.setSongs(new HashSet<>());
    User user = new User();
    user.setId(1L);
    user.setUsername("testUsername");
    user.setFirstName("First");
    user.setLastName("Last");
    user.setPlaylists(new HashSet<>());

    Song song = new Song();
    song.setId(1L);
    song.setTitle("Title tes");
    song.setLyrics("dfjaskfnsjgf fsdfjiodsgf fgsdfjisdifg ");
    Author author = new Author();
    author.setId(1L);
    author.setName("Author Name");
    author.setSongs(new HashSet<>());
    author.addSong(song);
    playlist.addSong(song);

    user.addPlaylist(playlist);

    AtomicReference<String> fileName = new AtomicReference<>("");
    assertDoesNotThrow(() -> fileName.set(pdfService.createPdfFromPlaylist(playlist)));
    File file = new File(fileSystemStorageService.getLocation().resolve(fileName.get()).toAbsolutePath().toString());
    assertTrue(file.exists());
    assertTrue(file.delete());
  }
}

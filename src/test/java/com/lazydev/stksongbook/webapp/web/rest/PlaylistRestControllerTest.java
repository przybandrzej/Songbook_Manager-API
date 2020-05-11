package com.lazydev.stksongbook.webapp.web.rest;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.service.FileSystemStorageService;
import com.lazydev.stksongbook.webapp.service.PdfService;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
class PlaylistRestControllerTest {

  @MockBean
  private PdfService pdfService;

  @MockBean
  private PlaylistService service;

  @MockBean
  private PlaylistMapper mapper;

  @MockBean
  private FileSystemStorageService storageService;

  private MockMvc mockMvc;
  private PlaylistRestController controller;
  private static final String endpoint = "/api/playlists";

  @BeforeEach
  void setUp() {
    controller = new PlaylistRestController(service, mapper, pdfService, storageService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  private Playlist getSamplePrivatePlaylist() {
    Playlist playlist = new Playlist();
    playlist.setName("testing playlist title");
    User user = new User();
    user.setUsername("playlist owner");
    playlist.setOwner(user);
    Song song = new Song();
    song.setTitle("testing song 1 title");
    Author author = new Author();
    author.setName("song author");
    song.setAuthor(author);
    song.setLyrics("Lorem ipsum dolor sit amet, \nconsectetur adipiscing elit, \nsed do eiusmod tempor incididunt ut labore et dolore magna aliqua. \nUt enim ad minim veniam, \nquis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. \nDuis aute irure dolor in reprehenderit \nin voluptate velit esse cillum dolore eu fugiat nulla pariatur. \nExcepteur sint occaecat cupidatat non proident, \nsunt in culpa qui officia deserunt mollit anim id est laborum.");
    song.setGuitarTabs("sample tabs");
    playlist.addSong(song);
    playlist.setPrivate(true);
    return playlist;
  }

  private Playlist getSamplePublicPlaylist() {
    var playlist = getSamplePrivatePlaylist();
    playlist.setPrivate(false);
    playlist.setName("public one");
    return playlist;
  }

  @Test
  void getAll() throws Exception {
    given(service.findAll(true)).willReturn(List.of(getSamplePrivatePlaylist()));
    given(service.findAll(false)).willReturn(List.of(getSamplePublicPlaylist()));
    // TODO mock mapper

    // Exclude private playlists
    mockMvc.perform(MockMvcRequestBuilders.get(endpoint + "?include_private=false"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("false"));

    // Include private playlists
    mockMvc.perform(MockMvcRequestBuilders.get(endpoint + "?include_private=true"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("true"));
  }

  // there is nothing to test
  /* @Test public void downloadPlaylistPdfSongbook() { } */
}

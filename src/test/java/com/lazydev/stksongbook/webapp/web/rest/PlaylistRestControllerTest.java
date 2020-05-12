package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.service.FileSystemStorageService;
import com.lazydev.stksongbook.webapp.service.PdfService;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
class PlaylistRestControllerTest {
  /**
   * Tests for all GET methods (including download)
   * and DELETE make no sense since all the work is performed by mappers and services.
   * The only tested methods are the Create and Update.
   */

  private PlaylistService service;
  private PlaylistMapper mapper;
  private MockMvc mockMvc;

  private PlaylistRestController controller;
  private static final String endpoint = "/api/playlists";

  @BeforeEach
  void setUp() {
    PdfService pdfService = mock(PdfService.class);
    service = mock(PlaylistService.class);
    mapper = mock(PlaylistMapper.class);
    FileSystemStorageService storageService = mock(FileSystemStorageService.class);
    controller = new PlaylistRestController(service, mapper, pdfService, storageService);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void create() throws Exception {
    CreatePlaylistDTO validDto = CreatePlaylistDTO.builder().isPrivate(true).name("test playlist").ownerId(1L).songs(Set.of(1L, 2L, 3L)).build();
    CreatePlaylistDTO invalidDto = CreatePlaylistDTO.builder().isPrivate(true).name("test playlist").ownerId(1L).songs(null).build();
    CreatePlaylistDTO invalidDto2 = CreatePlaylistDTO.builder().isPrivate(true).name("t").ownerId(1L).songs(Set.of(1L, 2L, 3L)).build();
    CreatePlaylistDTO invalidDto3 = CreatePlaylistDTO.builder().isPrivate(true).name("test playlist").ownerId(null).songs(Set.of(1L, 2L, 3L)).build();

    Playlist validMapped = mockMap(validDto);
    given(mapper.map(validDto)).willReturn(validMapped);
    Playlist validSaved = mockSaved(validMapped, 1L);
    given(service.save(validMapped)).willReturn(validSaved);
    PlaylistDTO dto = mockMap(validSaved);
    given(mapper.map(validSaved)).willReturn(dto);

    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(validDto)))
        .andExpect(status().isCreated())
        .andExpect(content().json(convertObjectToJsonString(dto)));
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto2)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto3)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void update() {
    PlaylistDTO validDto = mockMap(getSamplePrivatePlaylist());
    PlaylistDTO invalidDto = PlaylistDTO.builder().isPrivate(true).name("test playlist").ownerId(1L).songs(Set.of(1L, 2L, 3L)).id(2L).create();
    given(service.findByIdNoException(1L, true)).willReturn(Optional.of(getSamplePrivatePlaylist()));
    given(service.findByIdNoException(2L, true)).willReturn(Optional.empty());

    given(mapper.map(validDto)).willReturn(getSamplePrivatePlaylist());
    Playlist validSaved = mockSaved(getSamplePrivatePlaylist(), 1L);
    given(service.save(getSamplePrivatePlaylist())).willReturn(validSaved);
    PlaylistDTO dto = mockMap(validSaved);
    given(mapper.map(validSaved)).willReturn(dto);

    assertEquals(HttpStatus.OK, controller.update(validDto).getStatusCode());
    assertThrows(EntityNotFoundException.class, () -> controller.update(invalidDto));
  }

  private Playlist getSamplePrivatePlaylist() {
    Playlist playlist = new Playlist();
    playlist.setId(1L);
    playlist.setName("testing playlist title");
    User user = new User();
    user.setUsername("playlist owner");
    playlist.setOwner(user);
    playlist.setSongs(new HashSet<>());
    Song song = new Song();
    song.setId(1L);
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

  private Playlist mockMap(CreatePlaylistDTO dto) {
    Playlist playlist = new Playlist();
    playlist.setName(dto.getName());
    playlist.setPrivate(dto.getIsPrivate());
    User owner = new User();
    owner.setId(dto.getOwnerId());
    playlist.setOwner(owner);
    playlist.setSongs(dto.getSongs().stream().map(it -> {
      Song s = new Song();
      s.setId(it);
      return s;
    }).collect(Collectors.toSet()));
    return playlist;
  }

  private Playlist mockSaved(Playlist pl, Long id) {
    Playlist playlist = new Playlist();
    playlist.setId(id);
    playlist.setOwner(pl.getOwner());
    playlist.setSongs(pl.getSongs());
    playlist.setPrivate(pl.isPrivate());
    playlist.setName(pl.getName());
    return playlist;
  }

  private PlaylistDTO mockMap(Playlist playlist) {
    return PlaylistDTO.builder()
        .id(playlist.getId())
        .isPrivate(playlist.isPrivate())
        .name(playlist.getName())
        .ownerId(playlist.getOwner().getId())
        .songs(playlist.getSongs().stream().mapToLong(Song::getId).boxed().collect(Collectors.toSet()))
        .create();
  }

  private String convertObjectToJsonString(PlaylistDTO dto) throws JsonProcessingException {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return writer.writeValueAsString(dto);
  }
}

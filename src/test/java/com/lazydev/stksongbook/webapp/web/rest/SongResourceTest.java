package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.service.FileSystemStorageService;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.*;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import com.lazydev.stksongbook.webapp.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SongResourceTest {
  /**
   * Tests for all GET methods and DELETE make no sense since all the work is performed by mappers and services.
   * The only tested methods are the Create and Update.
   */

  @Mock
  private SongService service;
  @Mock
  private SongMapper mapper;
  @Mock
  private UserSongRatingMapper userSongRatingMapper;
  @Mock
  private UserMapper userMapper;
  @Mock
  private PlaylistMapper playlistMapper;
  @Mock
  private FileSystemStorageService storageService;
  @InjectMocks
  private SongResource controller;

  private MockMvc mockMvc;
  private static final String endpoint = "/api/songs";

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ExceptionTranslator.class).build();
  }

  @Test
  void testCreate() throws Exception {
    CreateSongDTO song1 = getDtoFromFile().toBuilder().authorName("").build();
    CreateSongDTO song2 = getDtoFromFile().toBuilder().title(null).build();
    CreateSongDTO song3 = getDtoFromFile();

    Song mapped = map(song3);
    given(service.createAndSaveSong(song3)).willReturn(mapped);
    given(mapper.map(any(Song.class))).willAnswer(it -> {
      Song a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(song3)))
        .andExpect(status().isCreated())
        .andExpect(content().json(convertObjectToJsonString(map(mapped))));
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(song1)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(song2)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testUpdate() throws Exception {
    Song song = getSampleSong();
    SongDTO validDto = map(song);
    Song invalidSong = getSampleSong();
    invalidSong.setId(2L);
    SongDTO invalidDto = map(invalidSong);
    given(service.findByIdNoException(1L)).willReturn(Optional.of(song));
    given(service.findByIdNoException(2L)).willReturn(Optional.empty());

    given(mapper.map(validDto)).willReturn(song);
    given(service.save(song)).willReturn(song);
    SongDTO dto = map(song);
    given(mapper.map(song)).willReturn(dto);

    mockMvc.perform(MockMvcRequestBuilders.put(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto)))
        .andExpect(status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.put(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(validDto)))
        .andDo(mvcResult -> System.out.println(mvcResult.getResponse().getStatus()))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(dto)));
  }

  @Test
  void testGetById() throws Exception {
    Song song = getSampleSong();
    given(service.findById(1L)).willReturn(song);
    given(service.findById(2L)).willThrow(EntityNotFoundException.class);
    given(mapper.map(any(Song.class))).willAnswer(it -> {
      Song a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.get(endpoint + "/id/2"))
        .andExpect(status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.get(endpoint + "/id/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(map(song))));
  }

  @Test
  void loadFromFile() throws Exception {
    given(storageService.getLocation()).willReturn(Paths.get("./test_storage"));
    Path file = storageService.getLocation().resolve("song.json");
    byte[] data = Files.readAllBytes(file);

    MockMultipartFile firstFile = new MockMultipartFile("file", "filename.json", "text/plain", "some xml".getBytes());
    MockMultipartFile secondFile = new MockMultipartFile("file", "other-file-name.json", "text/plain", "some other type".getBytes());
    MockMultipartFile thirdFile = new MockMultipartFile("file", "song.json", "text/plain", data);
    MockMultipartFile fourthFile = new MockMultipartFile("file", "filename", "text/plain", "some xml".getBytes());

    given(storageService.store(thirdFile)).willReturn("song.json");
    given(storageService.store(firstFile)).willReturn("filename.json");
    given(storageService.store(secondFile)).willReturn("other-file-name.json");
    given(storageService.store(fourthFile)).willReturn("filename");
    given(service.readSongFromFile("filename.json")).willThrow(IOException.class);
    given(service.readSongFromFile("other-file-name.json")).willThrow(IOException.class);
    given(service.readSongFromFile("filename")).willThrow(IOException.class);
    CreateSongDTO dto = getDtoFromFile();
    given(service.readSongFromFile("song.json")).willReturn(dto);
    given(service.createAndSaveSong(dto)).willReturn(new Song());
    given(mapper.map(any(Song.class))).willReturn(SongDTO.builder().build());

    mockMvc.perform(MockMvcRequestBuilders.multipart(endpoint + "/upload")
        .file(firstFile))
        .andExpect(status().is(500));
    mockMvc.perform(MockMvcRequestBuilders.multipart(endpoint + "/upload")
        .file(secondFile))
        .andExpect(status().is(500));
    mockMvc.perform(MockMvcRequestBuilders.multipart(endpoint + "/upload")
        .file(fourthFile))
        .andExpect(status().is(500));
    mockMvc.perform(MockMvcRequestBuilders.multipart(endpoint + "/upload")
        .file(thirdFile))
        .andExpect(status().is(200));
  }

  private Song getSampleSong() {
    Song song = new Song();
    song.setId(1L);
    song.setTitle("dummy title");
    song.setCoauthors(new HashSet<>());
    song.setTags(new HashSet<>());
    song.setGuitarTabs("ddddddddd");
    song.setLyrics("dasdafsgsdg gfdasgsd");
    song.setRatings(new HashSet<>());
    song.setUsersSongs(new HashSet<>());
    song.setPlaylists(new HashSet<>());

    Author author = new Author();
    author.setId(3L);
    author.setName("fdas");
    author.setSongs(new HashSet<>());
    author.setCoauthorSongs(new HashSet<>());
    author.addSong(song);

    Author author2 = new Author();
    author2.setId(2L);
    author2.setName("fdas");
    author2.setSongs(new HashSet<>());
    author2.setCoauthorSongs(new HashSet<>());

    Author author3 = new Author();
    author3.setId(1L);
    author3.setName("fdas");
    author3.setSongs(new HashSet<>());
    author3.setCoauthorSongs(new HashSet<>());

    SongCoauthor coauthor = new SongCoauthor();
    coauthor.setId(new SongsCoauthorsKey());
    coauthor.setAuthor(author2);
    coauthor.setSong(song);
    coauthor.setCoauthorFunction("muzyka");

    SongCoauthor coauthor2 = new SongCoauthor();
    coauthor2.setId(new SongsCoauthorsKey());
    coauthor2.setAuthor(author3);
    coauthor2.setSong(song);
    coauthor2.setCoauthorFunction("tekst");

    Category category = new Category();
    category.setId(5L);
    category.setName("dummy category");
    category.setSongs(new HashSet<>());
    song.setCategory(category);

    song.setCreationTime(LocalDateTime.now());
    song.addTag(getTag());

    UserSongRating rating = new UserSongRating();
    rating.setId(new UsersSongsRatingsKey());
    rating.setRating(0.9);
    User user = new User();
    user.setId(1L);
    user.setUserRatings(new HashSet<>());
    user.setSongs(new HashSet<>());
    user.setPlaylists(new HashSet<>());
    rating.setUser(user);
    rating.setSong(song);
    user.addSong(song);

    UserSongRating rating2 = new UserSongRating();
    rating2.setRating(0.8);
    rating2.setId(new UsersSongsRatingsKey());
    User user2 = new User();
    user2.setUserRatings(new HashSet<>());
    user2.setId(2L);
    rating2.setUser(user2);
    rating2.setSong(song);

    song.setRatings(Set.of(rating, rating2));

    Playlist playlist = new Playlist();
    playlist.setSongs(new HashSet<>());
    playlist.setId(1L);
    playlist.addSong(song);
    user.addPlaylist(playlist);

    song.setAwaiting(true);

    return song;
  }

  private Tag getTag() {
    Tag tag = new Tag();
    tag.setId(6L);
    tag.setName("dummy tag");
    tag.setSongs(new HashSet<>());
    return tag;
  }

  private Song map(SongDTO dto) {
    Song song = new Song();
    song.setId(dto.getId());
    Author author = new Author();
    author.setId(dto.getAuthor().getId());
    author.setName(dto.getAuthor().getName());
    author.setSongs(new HashSet<>());
    author.addSong(song);
    song.setAwaiting(dto.getIsAwaiting());
    song.setUsersSongs(new HashSet<>());
    Category category = new Category();
    category.setId(dto.getCategory().getId());
    category.setName(dto.getCategory().getName());
    category.setSongs(new HashSet<>());
    song.setCategory(category);
    song.setPlaylists(new HashSet<>());
    song.setRatings(new HashSet<>());
    song.setTitle(dto.getTitle());
    song.setLyrics(dto.getLyrics());
    song.setGuitarTabs(dto.getGuitarTabs());
    song.setTags(new HashSet<>());
    dto.getTags().forEach(it -> {
      song.addTag(new Tag(it.getId(), it.getName(), new HashSet<>()));
    });
    song.setCoauthors(new HashSet<>());
    dto.getCoauthors().forEach(it -> {
      SongCoauthor coauthor = new SongCoauthor();
      coauthor.setId(new SongsCoauthorsKey());
      coauthor.setCoauthorFunction(it.getCoauthorFunction());
      coauthor.setAuthor(new Author(it.getAuthorId(), "dummy", null, null, new HashSet<>(), new HashSet<>()));
      coauthor.setSong(song);
    });
    return song;
  }

  private Song map(CreateSongDTO dto) {
    Song song = new Song();
    song.setId(Constants.DEFAULT_ID);
    Author author = new Author();
    author.setId(2L);
    author.setName(dto.getAuthorName());
    author.setSongs(new HashSet<>());
    author.addSong(song);
    song.setUsersSongs(new HashSet<>());
    Category category = new Category();
    category.setId(dto.getCategoryId());
    category.setName("dummy");
    category.setSongs(new HashSet<>());
    song.setCategory(category);
    song.setPlaylists(new HashSet<>());
    song.setRatings(new HashSet<>());
    song.setTitle(dto.getTitle());
    song.setLyrics(dto.getLyrics());
    song.setGuitarTabs(dto.getGuitarTabs());
    song.setTags(new HashSet<>());
    song.setAwaiting(true);
    dto.getTags().forEach(it -> {
      song.addTag(new Tag(2L, it, new HashSet<>()));
    });
    song.setCoauthors(new HashSet<>());
    dto.getCoauthors().forEach(it -> {
      SongCoauthor coauthor = new SongCoauthor();
      coauthor.setId(new SongsCoauthorsKey());
      coauthor.setCoauthorFunction(it.getCoauthorFunction());
      coauthor.setAuthor(new Author(2L, it.getAuthorName(), null, null, new HashSet<>(), new HashSet<>()));
      coauthor.setSong(song);
    });
    return song;
  }

  private SongDTO map(Song song) {
    AuthorDTO authorDTO = AuthorDTO.builder().id(song.getAuthor().getId()).name(song.getAuthor().getName()).build();
    CategoryDTO categoryDTO = CategoryDTO.builder().id(song.getCategory().getId()).name(song.getCategory().getName()).build();
    List<TagDTO> tagDTOS = song.getTags().stream().map(it -> TagDTO.builder().id(it.getId()).name(it.getName()).build()).collect(Collectors.toList());
    Set<SongCoauthorDTO> coauthorDTOS = song.getCoauthors().stream().map(it -> SongCoauthorDTO.builder().songId(it.getSong().getId())
        .authorId(it.getAuthor().getId()).coauthorFunction(it.getCoauthorFunction()).build()).collect(Collectors.toSet());
    return SongDTO.builder().id(song.getId()).title(song.getTitle()).author(authorDTO).lyrics(song.getLyrics()).category(categoryDTO)
        .averageRating(0.0).guitarTabs(song.getGuitarTabs()).tags(tagDTOS).coauthors(coauthorDTOS).isAwaiting(song.isAwaiting()).build();
  }

  private String convertObjectToJsonString(SongDTO dto) throws JsonProcessingException {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return writer.writeValueAsString(dto);
  }

  private CreateSongDTO getDtoFromFile() {
    CreateCoauthorDTO dto1 = CreateCoauthorDTO.builder().authorName("Generalo").coauthorFunction("muzyka").build();
    CreateCoauthorDTO dto2 = CreateCoauthorDTO.builder().authorName("Andrzej").coauthorFunction("tekst").build();
    return CreateSongDTO.builder().authorName("Ziutek").categoryId(1L).trivia(null).guitarTabs("ABBA CD E F").lyrics("fjdksnfldsfnsdjklfndkl;sfndsl;kfndkls\ndsavdgsvbhaj")
        .tags(List.of("tag1", "tag2", "tag4")).title("sample title").coauthors(Set.of(dto1, dto2)).build();
  }
}

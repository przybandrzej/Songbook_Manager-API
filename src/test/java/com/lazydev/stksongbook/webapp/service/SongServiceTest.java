package com.lazydev.stksongbook.webapp.service;

import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.repository.SongAddRepository;
import com.lazydev.stksongbook.webapp.repository.SongEditRepository;
import com.lazydev.stksongbook.webapp.repository.SongRepository;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class SongServiceTest {

  @Mock
  private SongRepository repository;
  @Mock
  private TagService tagService;
  @Mock
  private AuthorService authorService;
  @Mock
  private SongCoauthorService coauthorService;
  @Mock
  private CategoryService categoryService;
  @Mock
  private FileSystemStorageService storageService;
  @Mock
  private UserSongRatingService ratingService;
  @Mock
  private UserService userService;
  @Mock
  private SongAddRepository songAddRepository;
  @Mock
  private SongEditRepository songEditRepository;
  @InjectMocks
  private SongService songService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void testFindById() {
    Song song = getSample();
    given(repository.findById(1L)).willReturn(Optional.of(song));
    given(repository.findById(2L)).willReturn(Optional.empty());

    assertDoesNotThrow(() -> songService.findById(1L));
    assertNotNull(songService.findById(1L));
    assertEquals(1L, songService.findById(1L).getId());

    assertThrows(EntityNotFoundException.class, () -> songService.findById(2L));
  }

  @Test
  void testDeleteById() {
    Song song = getSample();

    given(repository.findById(1L)).willReturn(Optional.of(song));
    given(repository.findById(2L)).willReturn(Optional.empty());
    willDoNothing().given(repository).deleteById(1L);

    assertThrows(EntityNotFoundException.class, () -> songService.deleteById(2L));
    assertDoesNotThrow(() -> songService.deleteById(1L));
  }

  @Test
  void testCreateAndSaveSong() {
    CreateCoauthorDTO coauthorDTO = CreateCoauthorDTO.builder().authorName("coauthor").coauthorFunction("muzyka").build();
    CreateCoauthorDTO coauthorDTO2 = CreateCoauthorDTO.builder().authorName("coauthor2").coauthorFunction("tekst").build();
    CreateSongDTO dto = CreateSongDTO.builder().authorName("author").categoryId(1L).title("title s").trivia(null)
        .lyrics("dsa fa fda").tags(List.of("tag1", "tag2")).guitarTabs("gfsdgsjhifs")
        .coauthors(Set.of(coauthorDTO, coauthorDTO2)).build();

    Tag tag1 = new Tag(1L, "tag1", new HashSet<>());
    Tag tag2 = new Tag(2L, "tag2", new HashSet<>());
    Author author1 = new Author(1L, "author", null, null, new HashSet<>(), new HashSet<>());
    Author author2 = new Author(1L, "coauthor", null, null, new HashSet<>(), new HashSet<>());
    Author author3 = new Author(1L, "coauthor2", null, null, new HashSet<>(), new HashSet<>());
    Category category = new Category(1L, "category", new HashSet<>());

    User user = new User();
    user.setId(1L);
    given(userService.findById(1L)).willReturn(user);
    given(categoryService.findById(1L)).willReturn(category);
    given(tagService.findOrCreateTag("tag1")).willReturn(tag1);
    given(tagService.findOrCreateTag("tag2")).willReturn(tag2);
    given(authorService.findOrCreateAuthor("author")).willReturn(author1);
    given(authorService.findOrCreateAuthor("coauthor")).willReturn(author2);
    given(authorService.findOrCreateAuthor("coauthor2")).willReturn(author3);
    given(repository.save(any(Song.class))).willAnswer(result -> {
      Song song = result.getArgument(0);
      song.setId(2L);
      return song;
    });
    given(coauthorService.findOrCreate(any(Song.class), eq(author2), eq("muzyka"))).willAnswer(result -> {
      Song song = result.getArgument(0);
      SongCoauthor coauthor = new SongCoauthor();
      coauthor.setId(new SongsCoauthorsKey());
      coauthor.setAuthor(result.getArgument(1));
      coauthor.setSong(song);
      coauthor.setCoauthorFunction(result.getArgument(2));
      return coauthor;
    });
    given(coauthorService.findOrCreate(any(Song.class), eq(author3), eq("tekst"))).willAnswer(result -> {
      Song song = result.getArgument(0);
      SongCoauthor coauthor = new SongCoauthor();
      coauthor.setId(new SongsCoauthorsKey());
      coauthor.setAuthor(result.getArgument(1));
      coauthor.setSong(song);
      coauthor.setCoauthorFunction(result.getArgument(2));
      return coauthor;
    });

    assertDoesNotThrow(() -> songService.createAndSaveSong(dto));
    Song created = songService.createAndSaveSong(dto);
    assertEquals(dto.getAuthorName(), created.getAuthor().getName());
    assertEquals(dto.getCategoryId(), created.getCategory().getId());
    assertEquals(dto.getTitle(), created.getTitle());
    assertEquals(dto.getTrivia(), created.getTrivia());
    assertEquals(dto.getGuitarTabs(), created.getGuitarTabs());
    assertEquals(dto.getLyrics(), created.getLyrics());
    for(CreateCoauthorDTO entry : dto.getCoauthors()) {
      assertTrue(created.getCoauthors().stream().anyMatch(it -> it.getAuthor().getName().equals(entry.getAuthorName())));
      assertTrue(created.getCoauthors().stream().anyMatch(it -> it.getCoauthorFunction().equals(entry.getCoauthorFunction())));
    }
    for(String entry : dto.getTags()) {
      assertTrue(created.getTags().stream().anyMatch(it -> it.getName().equals(entry)));
    }
  }

  @Test
  void testReadSongFromFile() throws IOException {
    given(storageService.getLocation()).willReturn(Paths.get("./test_storage"));
    String fileName = "song.json";
    String wrongFile = "x.x";

    assertDoesNotThrow(() -> songService.readSongFromFile(fileName));
    assertThrows(IOException.class, () -> songService.readSongFromFile(wrongFile));
    CreateSongDTO dto = songService.readSongFromFile(fileName);

    assertEquals("Ziutek", dto.getAuthorName());
    assertEquals(1L, dto.getCategoryId());
    assertEquals("sample title", dto.getTitle());
    assertEquals(3, dto.getTags().size());
    assertEquals(2, dto.getCoauthors().size());
  }

  private Song getSample() {
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

    //song.setCreationTime(LocalDateTime.now());
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

    return song;
  }

  private Tag getTag() {
    Tag tag = new Tag();
    tag.setId(6L);
    tag.setName("dummy tag");
    tag.setSongs(new HashSet<>());
    return tag;
  }
}

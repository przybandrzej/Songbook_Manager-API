package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.StkSongbookApplication;
import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.UserSongRatingService;
import com.lazydev.stksongbook.webapp.service.dto.*;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {StkSongbookApplication.class, SongMapperImpl.class})
class SongMapperTest {

  @Mock
  private UserService userService;
  @Mock
  private UserSongRatingService ratingService;
  @Mock
  private PlaylistService playlistService;
  @Mock
  private TagMapper tagMapper;
  @Mock
  private CategoryMapper categoryMapper;
  @Mock
  private SongCoauthorMapper songCoauthorMapper;
  @Mock
  private AuthorMapper authorMapper;

  @Autowired
  private SongMapperImpl impl;
  private SongMapper mapper;

  @BeforeEach
  void setUp() {
    impl.setPlaylistService(playlistService);
    impl.setUserService(userService);
    impl.setUserSongRatingService(ratingService);
    SongMapperImpl_ delegate = new SongMapperImpl_(songCoauthorMapper, categoryMapper, authorMapper, tagMapper);
    impl.setDelegate(delegate);
    mapper = impl;
  }

  @Test
  void testMapToDTO() {
    Song song = getSample();
    SongDTO dto = mapper.map(song);

    assertEquals(song.getId(), dto.getId());
    assertEquals(song.getTitle(), dto.getTitle());
    assertEquals(song.getAuthor().getId(), dto.getAuthor().getId());
    assertEquals(song.getAuthor().getName(), dto.getAuthor().getName());
    assertEquals(song.getCategory().getId(), dto.getCategory().getId());
    assertEquals(song.getCategory().getName(), dto.getCategory().getName());
    assertEquals(song.getLyrics(), dto.getLyrics());
    assertEquals(song.getCurio(), dto.getCurio());
    assertEquals(song.getTags().size(), dto.getTags().size());
    assertEquals(song.getCoauthors().size(), dto.getCoauthors().size());
    assertEquals(song.getGuitarTabs(), dto.getGuitarTabs());
    OptionalDouble avg = song.getRatings().stream().mapToDouble(UserSongRating::getRating).average();
    assertTrue(avg.isPresent());
    assertEquals(avg.getAsDouble(), dto.getAverageRating());
  }

  @Test
  void testMapToEntity() {
    Song song = getSample();

    AuthorDTO authorDTO = AuthorDTO.builder().id(3L).name("fdas").build();
    TagDTO tagDTO = TagDTO.builder().id(6L).name("dummy tag").build();
    CategoryDTO categoryDTO = CategoryDTO.builder().id(5L).name("dummy category").build();
    Set<SongCoauthorDTO> coauthorDTOS = new HashSet<>();
    SongCoauthorDTO firstCoauthor = SongCoauthorDTO.builder().authorId(2L).songId(1L).create();
    SongCoauthorDTO secondCoauthor = SongCoauthorDTO.builder().authorId(1L).songId(1L).create();
    coauthorDTOS.add(firstCoauthor);
    coauthorDTOS.add(secondCoauthor);
    SongDTO dto = SongDTO.builder().id(1L).title("dummy title").lyrics("dasdafsgsdg gfdasgsd").guitarTabs("ddddddddd")
        .author(authorDTO).tags(List.of(tagDTO)).averageRating(0.75).category(categoryDTO).curio(null)
        .creationTime(song.getCreationTime().format(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT)))
        .coauthors(coauthorDTOS).build();

    given(tagMapper.map(tagDTO)).willReturn(getTag());
    given(categoryMapper.map(categoryDTO)).willReturn(song.getCategory());
    given(songCoauthorMapper.map(any(SongCoauthorDTO.class))).willAnswer(result -> {
      Set<SongCoauthor> set = song.getCoauthors().stream().filter(i -> {
        SongCoauthorDTO scd = result.getArgument(0);
        return scd.getAuthorId().equals(i.getAuthor().getId());
      }).collect(Collectors.toSet());
      return set.stream().findFirst().get();
    });
    given(authorMapper.map(authorDTO)).willReturn(song.getAuthor());
    given(userService.findBySong(any())).willReturn(Collections.emptyList());
    given(playlistService.findBySongId(any(), anyBoolean())).willReturn(Collections.emptyList());
    given(ratingService.findBySongId(1L)).willReturn(new ArrayList<>(song.getRatings()));

    Song mapped = mapper.map(dto);

    assertEquals(dto.getId(), mapped.getId());
    assertEquals(dto.getAuthor().getId(), mapped.getAuthor().getId());
    assertEquals(dto.getCategory().getId(), mapped.getCategory().getId());
    assertEquals(dto.getLyrics(), mapped.getLyrics());
    assertEquals(dto.getTitle(), mapped.getTitle());
    assertEquals(dto.getGuitarTabs(), mapped.getGuitarTabs());
    assertEquals(dto.getCurio(), mapped.getCurio());
    assertEquals(dto.getTags().size(), mapped.getTags().size());
    assertEquals(dto.getCoauthors().size(), mapped.getCoauthors().size());
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
    coauthor.setFunction("muzyka");

    SongCoauthor coauthor2 = new SongCoauthor();
    coauthor2.setId(new SongsCoauthorsKey());
    coauthor2.setAuthor(author3);
    coauthor2.setSong(song);
    coauthor2.setFunction("tekst");

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
    rating.setUser(user);
    rating.setSong(song);

    UserSongRating rating2 = new UserSongRating();
    rating2.setRating(0.8);
    rating2.setId(new UsersSongsRatingsKey());
    User user2 = new User();
    user2.setUserRatings(new HashSet<>());
    user2.setId(2L);
    rating2.setUser(user2);
    rating2.setSong(song);

    song.setRatings(Set.of(rating, rating2));

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

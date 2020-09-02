package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.StkSongbookApplication;
import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {StkSongbookApplication.class, UserSongRatingMapperImpl.class})
class UserSongRatingMapperTest {

  @Mock
  private SongService songService;
  @Mock
  private UserService userService;
  @Autowired
  private UserSongRatingMapperImpl impl;
  private UserSongRatingMapper mapper;

  @BeforeEach
  void setUp() {
    impl.setUserService(userService);
    impl.setSongService(songService);
    mapper = impl;
  }

  @Test
  void testMapToDTO() {
    UserSongRating userSongRating = getSample();

    UserSongRatingDTO dto = mapper.map(userSongRating);

    assertEquals(userSongRating.getUser().getId(), dto.getUserId());
    assertEquals(userSongRating.getSong().getId(), dto.getSongId());
    assertEquals(userSongRating.getRating(), dto.getRating());
  }

  @Test
  void testMapToEntity() {
    UserSongRatingDTO dto = UserSongRatingDTO.builder().userId(1L).songId(2L).rating(BigDecimal.valueOf(0.9)).build();
    UserSongRating coauthor = getSample();

    given(songService.findById(2L)).willReturn(coauthor.getSong());
    given(userService.findById(1L)).willReturn(coauthor.getUser());

    UserSongRating mapped = mapper.map(dto);

    assertEquals(dto.getUserId(), mapped.getUser().getId());
    assertEquals(dto.getSongId(), mapped.getSong().getId());
    assertEquals(dto.getRating(), mapped.getRating());
  }

  private UserSongRating getSample() {
    User user = new User();
    user.setId(1L);
    user.setUsername("author name");
    user.setUserRatings(new HashSet<>());
    Song song = new Song();
    song.setId(2L);
    song.setTitle("test title");
    song.setRatings(new HashSet<>());
    UserSongRating userSongRating = new UserSongRating();
    userSongRating.setId(new UsersSongsRatingsKey());
    userSongRating.setSong(song);
    userSongRating.setUser(user);
    userSongRating.setRating(BigDecimal.valueOf(0.9));
    return userSongRating;
  }
}

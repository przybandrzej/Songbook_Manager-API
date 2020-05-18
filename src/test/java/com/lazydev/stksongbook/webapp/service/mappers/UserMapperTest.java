package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.StkSongbookApplication;
import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.service.*;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.RegisterNewUserForm;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {StkSongbookApplication.class, UserMapperImpl.class})
class UserMapperTest {

  @Mock
  private UserRoleService userRoleService;
  @Mock
  private SongService songService;
  @Mock
  private UserSongRatingService userSongRatingService;
  @Mock
  private PlaylistService playlistService;
  @Mock
  private UserService userService;

  @Autowired
  private UserMapperImpl impl;

  private UserMapper mapper;

  @BeforeEach
  void setUp() {
    impl.setUserService(userService);
    impl.setPlaylistService(playlistService);
    impl.setSongService(songService);
    impl.setUserRoleService(userRoleService);
    impl.setUserSongRatingService(userSongRatingService);
    mapper = impl;
  }

  @Test
  void testMapToDTO() {
    User user = new User();
    user.setId(1L);
    user.setUsername("dummy name");
    user.setSongs(new HashSet<>());
    UserRole role = new UserRole();
    role.setId(2L);
    role.setUsers(new HashSet<>());
    user.setUserRole(role);
    user.setFirstName("First");
    user.setLastName("Last");
    Song song = new Song();
    song.setId(3L);
    user.setSongs(Set.of(song));

    UserDTO dto = mapper.map(user);

    assertEquals(user.getUsername(), dto.getUsername());
    assertEquals(user.getId(), dto.getId());
    assertEquals(user.getFirstName(), dto.getFirstName());
    assertEquals(user.getLastName(), dto.getLastName());
    assertEquals(user.getUserRole().getId(), dto.getUserRoleId());
    assertEquals(user.getSongs().size(), dto.getSongs().size());
    assertTrue(user.getSongs().stream().mapToLong(Song::getId).allMatch(i -> i == song.getId()));
  }

  @Test
  void testMapToEntity() {
    User user = new User();
    user.setId(1L);
    user.setUsername("dummy name");
    user.setSongs(new HashSet<>());
    user.setPlaylists(new HashSet<>());
    user.setUserRatings(new HashSet<>());
    UserRole role = new UserRole();
    role.setId(2L);
    role.setUsers(new HashSet<>());
    user.setUserRole(role);
    user.setFirstName("First");
    user.setLastName("Last");
    Song song = new Song();
    song.setId(3L);
    song.setRatings(new HashSet<>());
    user.setSongs(Set.of(song));
    Playlist playlist = new Playlist();
    playlist.setId(1L);
    user.addPlaylist(playlist);
    UserSongRating rating = new UserSongRating();
    rating.setId(new UsersSongsRatingsKey());
    rating.setSong(song);
    rating.setUser(user);
    rating.setRating(0.9);

    UserDTO dto = UserDTO.builder().id(1L).username("dummy name").firstName("First").lastName("Last").userRoleId(2L)
        .songs(Set.of(3L)).build();

    given(userService.findById(1L)).willReturn(user);
    given(songService.findById(3L)).willReturn(song);
    given(userRoleService.findById(2L)).willReturn(role);
    given(userSongRatingService.findByUserId(1L)).willReturn(List.of(rating));
    given(playlistService.findByOwnerId(1L, true)).willReturn(List.of(playlist));
    User mapped = mapper.map(dto);

    assertEquals(dto.getId(), mapped.getId());
    assertEquals(dto.getUsername(), mapped.getUsername());
    assertEquals(1, mapped.getSongs().size());
    assertTrue(mapped.getSongs().contains(song));
    assertEquals(dto.getFirstName(), mapped.getFirstName());
    assertEquals(dto.getLastName(), mapped.getLastName());
    assertEquals(user.getEmail(), mapped.getEmail());
    assertEquals(user.getPassword(), mapped.getPassword());
    assertEquals(dto.getUserRoleId(), mapped.getUserRole().getId());
    assertEquals(1, mapped.getPlaylists().size());
    assertTrue(mapped.getPlaylists().contains(playlist));
    assertEquals(1, mapped.getUserRatings().size());
    assertTrue(mapped.getUserRatings().contains(rating));
  }

  @Test
  void testMapFromRegisterForm() {
    RegisterNewUserForm dto = RegisterNewUserForm.builder().email("a@a.pl").firstName("First")
        .lastName("Last").password("password").username("username").build();

    UserRole role = new UserRole();
    role.setName("user");
    role.setId(3L);
    role.setUsers(new HashSet<>());
    given(userRoleService.findById(Constants.CONST_USER_ID)).willReturn(role);
    User user = mapper.mapFromRegisterForm(dto);

    assertEquals(dto.getUsername(), user.getUsername());
    assertEquals(Constants.DEFAULT_ID, user.getId());
    assertEquals(dto.getEmail(), user.getEmail());
    assertEquals(dto.getFirstName(), user.getFirstName());
    assertEquals(dto.getLastName(), user.getLastName());
    assertEquals(dto.getPassword(), user.getPassword());
    assertTrue(user.getUserRatings().isEmpty());
    assertTrue(user.getPlaylists().isEmpty());
    assertTrue(user.getSongs().isEmpty());
  }
}

package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.StkSongbookApplication;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {StkSongbookApplication.class, UserRoleMapperImpl.class})
class UserRoleMapperTest {

  @Mock
  private UserService userService;

  @Autowired
  private UserRoleMapperImpl impl;

  private UserRoleMapper mapper;

  @BeforeEach
  void setUp() {
    impl.setUserService(userService);
    mapper = impl;
  }

  @Test
  void testMapToDTO() {
    UserRole userRole = new UserRole();
    userRole.setId(1L);
    userRole.setName("dummy name");
    userRole.setUsers(new HashSet<>());

    UserRoleDTO dto = mapper.map(userRole);

    assertEquals(userRole.getName(), dto.getName());
    assertEquals(userRole.getId(), dto.getId());
  }

  @Test
  void testMapToEntity() {
    UserRoleDTO userRole = UserRoleDTO.builder().id(1L).name("dummy name").build();
    User user = new User();
    user.setId(1L);
    user.setUsername("username");

    given(userService.findByUserRole(1L)).willReturn(List.of(new User[]{user}));
    UserRole mapped = mapper.map(userRole);

    assertEquals(userRole.getId(), mapped.getId());
    assertEquals(userRole.getName(), mapped.getName());
    assertEquals(1, mapped.getUsers().size());
    assertTrue(mapped.getUsers().contains(user));
  }

  @Test
  void testMapFromUniversalCreateDTO() {
    UniversalCreateDTO dto = UniversalCreateDTO.builder().id(5L).name("dummy name").build();

    UserRole userRole = mapper.map(dto);

    assertEquals(dto.getName(), userRole.getName());
    assertNotEquals(dto.getId(), userRole.getId());
    assertEquals(Constants.DEFAULT_ID, userRole.getId());
    assertEquals(0, userRole.getUsers().size());
  }
}

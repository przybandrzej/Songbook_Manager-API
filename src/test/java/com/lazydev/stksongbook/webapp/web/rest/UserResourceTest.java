package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.UserService;
import com.lazydev.stksongbook.webapp.service.dto.LoginForm;
import com.lazydev.stksongbook.webapp.service.dto.UserDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.RegisterNewUserForm;
import com.lazydev.stksongbook.webapp.service.exception.UserNotExistsException;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import com.lazydev.stksongbook.webapp.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserResourceTest {
  /**
   * Tests for all GET and DELETE methods make no sense since all the work is performed by mappers and services.
   * The only tested methods are the Create, Update and one GET.
   */

  @Mock
  private PlaylistMapper playlistMapper;
  @Mock
  private UserSongRatingMapper userSongRatingMapper;
  @Mock
  private UserService service;
  @Mock
  private UserMapper mapper;
  @InjectMocks
  private UserResource controller;
  private MockMvc mockMvc;

  private static final String endpoint = "/api/users";

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ExceptionTranslator.class).build();
  }

  /*@Test
  void testRegister() throws Exception {
    RegisterNewUserForm validDto = RegisterNewUserForm.builder().email("a@aa.pl").firstName("First").lastName(null)
        .password("Abcdef12").username("username").build();
    RegisterNewUserForm invalidDto = RegisterNewUserForm.builder().email("a@").firstName("First").lastName(null)
        .password("Abcdef12").username("username").build();
    RegisterNewUserForm invalidDto2 = RegisterNewUserForm.builder().email("a@aa.pl").firstName("First").lastName(null)
        .password("Abc").username("username").build();
    RegisterNewUserForm invalidDto3 = RegisterNewUserForm.builder().email("a@aa.pl").firstName("First").lastName(null)
        .password("Abcdef12").username("us").build();
    RegisterNewUserForm invalidDto4 = RegisterNewUserForm.builder().email("a@aa.pl").firstName("First").lastName(null)
        .password("bcdefgh").username("username").build();
    RegisterNewUserForm invalidDto5 = RegisterNewUserForm.builder().email("a@aa.pl").firstName(null).lastName(null)
        .password("Abcdef12").username("username2").build();

    given(service.findByEmailNoException("a@aa.pl")).willReturn(Optional.empty());
    User validMapped = mapRegister(validDto);
    given(mapper.mapFromRegisterForm(validDto)).willReturn(validMapped);
    given(service.save(validMapped)).willAnswer(it -> {
      User u = it.getArgument(0);
      u.setId(2L);
      return u;
    });
    given(mapper.map(any(User.class))).willAnswer(it -> {
      User u = it.getArgument(0);
      return map(u);
    });
    validMapped.setId(2L);

    mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/register").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(validDto)))
        .andExpect(status().isCreated())
        .andExpect(content().json(convertObjectToJsonString(map(validMapped))));
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/register").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/register").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto2)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/register").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto3)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/register").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto4)))
        .andExpect(status().isBadRequest());
    given(service.findByEmailNoException("a@aa.pl")).willReturn(Optional.of(validMapped));
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/register").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto5)))
        .andExpect(status().isBadRequest());
  }*/

  /*@Test
  void testLogin() throws Exception {
    LoginForm form = LoginForm.builder().login("a@a.pl").password("aaaaaa").build();
    LoginForm form2 = LoginForm.builder().login("ab@a.pl").password("aaaaaa").build();
    LoginForm form3 = LoginForm.builder().login("a@a.pl").password("bbbbbbb").build();
    User user = getSample();

    given(service.findByEmailNoException("a@a.pl")).willReturn(Optional.of(user));
    given(service.findByEmailNoException("ab@a.pl")).willReturn(Optional.empty());
    given(mapper.map(user)).willReturn(map(user));

    mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/login").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(form)))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(map(user))));
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/login").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(form2)))
        .andExpect(status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint + "/login").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(form3)))
        .andExpect(status().isUnauthorized());
  }*/

  @Test
  void testUpdate() throws Exception {
    User user = getSample();
    UserDTO validDto = map(user);
    UserDTO invalidDto = UserDTO.builder().id(2L).songs(new HashSet<>()).userRoleId(2L).username("username").build();
    given(service.findByIdNoException(1L)).willReturn(Optional.of(getSample()));
    given(service.findByIdNoException(2L)).willReturn(Optional.empty());

    given(mapper.map(validDto)).willReturn(user);
    given(service.save(user)).willReturn(user);
    UserDTO dto = map(user);
    given(mapper.map(user)).willReturn(dto);

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
    User user = getSample();
    given(service.findById(1L)).willReturn(user);
    given(service.findById(2L)).willThrow(UserNotExistsException.class);
    given(mapper.map(any(User.class))).willAnswer(it -> {
      User a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.get(endpoint + "/id/2"))
        .andExpect(status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.get(endpoint + "/id/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(map(user))));
  }

  private User getSample() {
    User user = new User();
    user.setId(1L);
    user.setUsername("username");
    user.setEmail("a@a.pl");
    user.setPassword("aaaaaa");
    UserRole role = new UserRole();
    role.setUsers(new HashSet<>());
    role.setId(3L);
    role.setName("user");
    user.setUserRole(role);
    user.setUserRatings(new HashSet<>());
    user.setPlaylists(new HashSet<>());
    user.setUserRatings(new HashSet<>());
    user.setSongs(new HashSet<>());
    return user;
  }

  private User mapRegister(RegisterNewUserForm dto) {
    User user = new User();
    user.setId(Constants.DEFAULT_ID);
    user.setUsername(dto.getUsername());
    user.setPlaylists(new HashSet<>());
    user.setSongs(new HashSet<>());
    user.setUserRatings(new HashSet<>());
    user.setLastName(dto.getLastName());
    user.setFirstName(dto.getFirstName());
    user.setUserRole(new UserRole(Constants.CONST_USER_ID, "user", new HashSet<>()));
    user.setEmail(dto.getEmail());
    user.setPassword(dto.getPassword());
    return user;
  }

  private UserDTO map(User user) {
    return UserDTO.builder().id(user.getId()).songs(user.getSongs().stream().mapToLong(Song::getId).boxed().collect(Collectors.toSet()))
        .userRoleId(user.getUserRole().getId()).lastName(user.getLastName()).firstName(user.getFirstName()).username(user.getUsername())
        .build();
  }

  private String convertObjectToJsonString(UserDTO dto) throws JsonProcessingException {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return writer.writeValueAsString(dto);
  }
}

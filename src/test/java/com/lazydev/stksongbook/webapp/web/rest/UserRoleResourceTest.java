package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.UserRole;
import com.lazydev.stksongbook.webapp.service.UserRoleService;
import com.lazydev.stksongbook.webapp.service.dto.UserRoleDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.UserMapper;
import com.lazydev.stksongbook.webapp.service.mappers.UserRoleMapper;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
class UserRoleResourceTest {

  private static final String BASE_URL = "/api/user_roles";

  @Mock
  UserRoleService userRoleService;
  @Mock
  UserRoleMapper mapper;
  @Mock
  UserMapper userMapper;
  @InjectMocks
  UserRoleResource controller;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ExceptionTranslator.class).build();
  }

  @Test
  void testCreate() throws Exception {
    UniversalCreateDTO dto = UniversalCreateDTO.builder().id(null).name("name").build();
    UniversalCreateDTO dto2 = UniversalCreateDTO.builder().id(2L).name("name2").build();
    UniversalCreateDTO dto3 = UniversalCreateDTO.builder().id(5L).name("").build();

    given(userRoleService.findByNameNoException("name")).willReturn(Optional.empty());
    given(userRoleService.findByNameNoException("name2")).willReturn(Optional.of(map(dto2)));
    given(userRoleService.save(any(UserRole.class))).willAnswer(it -> {
      UserRole a = it.getArgument(0);
      a.setId(2L);
      return a;
    });
    given(mapper.map(any(UniversalCreateDTO.class))).willAnswer(it -> {
      UniversalCreateDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(UserRole.class))).willAnswer(it -> {
      UserRole a = it.getArgument(0);
      return map(a);
    });

    UserRole saved = map(dto);
    saved.setId(2L);
    UserRoleDTO returned = map(saved);

    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(content().json(convertObjectToJsonString(returned)));
    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto2)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto3)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testUpdate() throws Exception {
    UserRoleDTO dto = UserRoleDTO.builder().id(1L).name("dummy name").create();
    UserRoleDTO dto1 = UserRoleDTO.builder().id(null).name("dummy name2").create();
    UserRoleDTO dto2 = UserRoleDTO.builder().id(3L).name("").create();

    given(userRoleService.findByIdNoException(1L)).willReturn(Optional.of(map(dto)));
    given(userRoleService.save(any(UserRole.class))).willAnswer(it -> {
      UserRole a = it.getArgument(0);
      return a;
    });
    given(mapper.map(any(UserRoleDTO.class))).willAnswer(it -> {
      UserRoleDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(UserRole.class))).willAnswer(it -> {
      UserRole a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(dto)));
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto1)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto2)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetById() throws Exception {
    List<UserRole> list = new ArrayList<>();
    given(userRoleService.findById(1L)).willAnswer(i -> {
      Long id = i.getArgument(0);
      UserRole userRole = new UserRole();
      userRole.setId(1L);
      userRole.setName("dummy name");
      userRole.setUsers(new HashSet<>());
      list.add(userRole);
      return userRole;
    });
    given(userRoleService.findById(2L)).willThrow(EntityNotFoundException.class);
    given(mapper.map(any(UserRole.class))).willAnswer(it -> {
      UserRole a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(map(list.get(0)))));
    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/2"))
        .andExpect(status().isNotFound());
  }

  private UserRoleDTO map(UserRole userRole) {
    return UserRoleDTO.builder().id(userRole.getId()).name(userRole.getName()).create();
  }

  private UserRole map(UniversalCreateDTO dto) {
    UserRole userRole = new UserRole();
    userRole.setId(Constants.DEFAULT_ID);
    userRole.setName(dto.getName());
    userRole.setUsers(new HashSet<>());
    return userRole;
  }

  private UserRole map(UserRoleDTO dto) {
    UserRole userRole = new UserRole();
    userRole.setId(dto.getId());
    userRole.setName(dto.getName());
    userRole.setUsers(new HashSet<>());
    return userRole;
  }

  private String convertObjectToJsonString(UserRoleDTO dto) throws JsonProcessingException {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return writer.writeValueAsString(dto);
  }
}

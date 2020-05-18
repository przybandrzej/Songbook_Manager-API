package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.service.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.AuthorMapper;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
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
class AuthorRestControllerTest {

  private static final String BASE_URL = "/api/authors";

  @Mock
  AuthorService authorService;
  @Mock
  SongMapper songMapper;
  @Mock
  AuthorMapper mapper;
  @InjectMocks
  AuthorRestController controller;

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

    given(authorService.findByNameNoException("name")).willReturn(Optional.empty());
    given(authorService.findByNameNoException("name2")).willReturn(Optional.of(map(dto2)));
    given(authorService.save(any(Author.class))).willAnswer(it -> {
      Author a = it.getArgument(0);
      a.setId(2L);
      return a;
    });
    given(mapper.map(any(UniversalCreateDTO.class))).willAnswer(it -> {
      UniversalCreateDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(Author.class))).willAnswer(it -> {
      Author a = it.getArgument(0);
      return map(a);
    });

    Author saved = map(dto);
    saved.setId(2L);
    AuthorDTO returned = map(saved);

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
    AuthorDTO dto = AuthorDTO.builder().id(1L).name("dummy name").create();
    AuthorDTO dto1 = AuthorDTO.builder().id(null).name("dummy name2").create();
    AuthorDTO dto2 = AuthorDTO.builder().id(3L).name("").create();

    given(authorService.findByIdNoException(1L)).willReturn(Optional.of(map(dto)));
    given(authorService.save(any(Author.class))).willAnswer(it -> {
      Author a = it.getArgument(0);
      a.setId(2L);
      return a;
    });
    given(mapper.map(any(AuthorDTO.class))).willAnswer(it -> {
      AuthorDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(Author.class))).willAnswer(it -> {
      Author a = it.getArgument(0);
      return map(a);
    });

    Author saved = map(dto);
    saved.setId(2L);
    AuthorDTO returned = map(saved);

    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(returned)));
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto1)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto2)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testGetById() throws Exception {
    List<Author> list = new ArrayList<>();
    given(authorService.findById(1L)).willAnswer(i -> {
      Long id = i.getArgument(0);
      Author author = new Author();
      author.setId(1L);
      author.setName("dummy name");
      author.setSongs(new HashSet<>());
      list.add(author);
      return author;
    });
    given(authorService.findById(2L)).willThrow(EntityNotFoundException.class);
    given(mapper.map(any(Author.class))).willAnswer(it -> {
      Author a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(map(list.get(0)))));
    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/2"))
        .andExpect(status().isNotFound());
  }

  private AuthorDTO map(Author author) {
    return AuthorDTO.builder().id(author.getId()).name(author.getName()).create();
  }

  private Author map(UniversalCreateDTO dto) {
    Author author = new Author();
    author.setId(Constants.DEFAULT_ID);
    author.setName(dto.getName());
    author.setCoauthorSongs(new HashSet<>());
    author.setSongs(new HashSet<>());
    author.setBiographyUrl(null);
    author.setPhotoResource(null);
    return author;
  }

  private Author map(AuthorDTO dto) {
    Author author = new Author();
    author.setId(dto.getId());
    author.setName(dto.getName());
    author.setPhotoResource(null);
    author.setBiographyUrl(null);
    author.setSongs(new HashSet<>());
    author.setCoauthorSongs(new HashSet<>());
    return author;
  }

  private String convertObjectToJsonString(AuthorDTO dto) throws JsonProcessingException {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return writer.writeValueAsString(dto);
  }
}

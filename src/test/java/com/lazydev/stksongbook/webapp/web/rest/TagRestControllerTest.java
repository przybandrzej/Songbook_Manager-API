package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.service.TagService;
import com.lazydev.stksongbook.webapp.service.dto.TagDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.service.mappers.TagMapper;
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
class TagRestControllerTest {

  private static final String BASE_URL = "/api/tags";

  @Mock
  TagService tagService;
  @Mock
  SongMapper songMapper;
  @Mock
  TagMapper mapper;
  @InjectMocks
  TagRestController controller;

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

    given(tagService.findByNameNoException("name")).willReturn(Optional.empty());
    given(tagService.findByNameNoException("name2")).willReturn(Optional.of(map(dto2)));
    given(tagService.save(any(Tag.class))).willAnswer(it -> {
      Tag a = it.getArgument(0);
      a.setId(2L);
      return a;
    });
    given(mapper.map(any(UniversalCreateDTO.class))).willAnswer(it -> {
      UniversalCreateDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(Tag.class))).willAnswer(it -> {
      Tag a = it.getArgument(0);
      return map(a);
    });

    Tag saved = map(dto);
    saved.setId(2L);
    TagDTO returned = map(saved);

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
    TagDTO dto = TagDTO.builder().id(1L).name("dummy name").create();
    TagDTO dto1 = TagDTO.builder().id(null).name("dummy name2").create();
    TagDTO dto2 = TagDTO.builder().id(3L).name("").create();

    given(tagService.findByIdNoException(1L)).willReturn(Optional.of(map(dto)));
    given(tagService.save(any(Tag.class))).willAnswer(it -> {
      Tag a = it.getArgument(0);
      a.setId(2L);
      return a;
    });
    given(mapper.map(any(TagDTO.class))).willAnswer(it -> {
      TagDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(Tag.class))).willAnswer(it -> {
      Tag a = it.getArgument(0);
      return map(a);
    });

    Tag saved = map(dto);
    saved.setId(2L);
    TagDTO returned = map(saved);

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
    List<Tag> list = new ArrayList<>();
    given(tagService.findById(1L)).willAnswer(i -> {
      Long id = i.getArgument(0);
      Tag tag = new Tag();
      tag.setId(1L);
      tag.setName("dummy name");
      tag.setSongs(new HashSet<>());
      list.add(tag);
      return tag;
    });
    given(tagService.findById(2L)).willThrow(EntityNotFoundException.class);
    given(mapper.map(any(Tag.class))).willAnswer(it -> {
      Tag a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(map(list.get(0)))));
    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/2"))
        .andExpect(status().isNotFound());
  }

  private TagDTO map(Tag tag) {
    return TagDTO.builder().id(tag.getId()).name(tag.getName()).create();
  }

  private Tag map(UniversalCreateDTO dto) {
    Tag tag = new Tag();
    tag.setId(Constants.DEFAULT_ID);
    tag.setName(dto.getName());
    tag.setSongs(new HashSet<>());
    return tag;
  }

  private Tag map(TagDTO dto) {
    Tag tag = new Tag();
    tag.setId(dto.getId());
    tag.setName(dto.getName());
    tag.setSongs(new HashSet<>());
    return tag;
  }

  private String convertObjectToJsonString(TagDTO dto) throws JsonProcessingException {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return writer.writeValueAsString(dto);
  }
}

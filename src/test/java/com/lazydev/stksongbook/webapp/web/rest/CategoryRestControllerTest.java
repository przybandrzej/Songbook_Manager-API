package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.service.FileSystemStorageService;
import com.lazydev.stksongbook.webapp.service.PdfService;
import com.lazydev.stksongbook.webapp.service.PlaylistService;
import com.lazydev.stksongbook.webapp.service.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.service.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreatePlaylistDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityAlreadyExistsException;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.CategoryMapper;
import com.lazydev.stksongbook.webapp.service.mappers.PlaylistMapper;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
class CategoryRestControllerTest {
  /**
   * Tests for all GET methods and DELETE make no sense since all the work is performed by mappers and services.
   * The only tested methods are the Create and Update.
   */

  private CategoryService service;
  private CategoryMapper mapper;
  private MockMvc mockMvc;

  private CategoryRestController controller;
  private static final String endpoint = "/api/categories";

  @BeforeEach
  void setUp() {
    service = mock(CategoryService.class);
    mapper = mock(CategoryMapper.class);
    SongMapper songMapper = mock(SongMapper.class);
    controller = new CategoryRestController(service, mapper, songMapper);
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void create() throws Exception {
    UniversalCreateDTO validDto = UniversalCreateDTO.builder().name("test cat").id(1L).build();
    UniversalCreateDTO validDto2 = UniversalCreateDTO.builder().name("test cat2").id(null).build();
    UniversalCreateDTO invalidDto = UniversalCreateDTO.builder().name("").id(1L).build();
    UniversalCreateDTO invalidDto2 = UniversalCreateDTO.builder().name(null).id(1L).build();
    UniversalCreateDTO invalidDto3 = UniversalCreateDTO.builder().name("test catdassdfertyuii").id(1L).build();
    UniversalCreateDTO invalidDto4 = UniversalCreateDTO.builder().name("already exists").id(1L).build();

    given(service.findByNameNoException("test cat")).willReturn(Optional.empty());
    given(service.findByNameNoException("")).willReturn(Optional.empty());
    given(service.findByNameNoException("already exists")).willReturn(Optional.of(new Category(1L, "already exisits", new HashSet<>())));

    Category validMapped = mockMap(validDto);
    given(mapper.map(validDto)).willReturn(validMapped);
    Category validSaved = mockSaved(validMapped, 1L);
    given(service.save(validMapped)).willReturn(validSaved);
    CategoryDTO dto = mockMap(validSaved);
    given(mapper.map(validSaved)).willReturn(dto);

    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(validDto)))
        .andExpect(status().isCreated())
        .andExpect(content().json(convertObjectToJsonString(dto)));

    given(service.findByNameNoException("test cat2")).willReturn(Optional.empty());
    Category validMapped2 = mockMap(validDto2);
    given(mapper.map(validDto2)).willReturn(validMapped2);
    Category validSaved2 = mockSaved(validMapped2, 1L);
    given(service.save(validMapped2)).willReturn(validSaved2);
    CategoryDTO dto2 = mockMap(validSaved2);
    given(mapper.map(validSaved2)).willReturn(dto2);

    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(validDto2)))
        .andExpect(status().isCreated())
        .andExpect(content().json(convertObjectToJsonString(dto2)));

    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto)))
        .andExpect(status().isBadRequest());

    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto2)))
        .andExpect(status().isBadRequest());

    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto3)))
        .andExpect(status().isBadRequest());

    assertThrows(EntityAlreadyExistsException.class, () -> controller.create(invalidDto4));
  }

  @Test
  void update() {
    CategoryDTO validDto = mockMap(getSampleCategory());
    CategoryDTO invalidDto = CategoryDTO.builder().name("test invalid").id(2L).create();
    given(service.findByIdNoException(1L)).willReturn(Optional.of(getSampleCategory()));
    given(service.findByIdNoException(2L)).willReturn(Optional.empty());

    given(mapper.map(validDto)).willReturn(getSampleCategory());
    Category validSaved = mockSaved(getSampleCategory(), 1L);
    given(service.save(getSampleCategory())).willReturn(validSaved);
    CategoryDTO dto = mockMap(validSaved);
    given(mapper.map(validSaved)).willReturn(dto);

    assertEquals(HttpStatus.OK, controller.update(validDto).getStatusCode());
    assertThrows(EntityNotFoundException.class, () -> controller.update(invalidDto));
  }

  private Category getSampleCategory() {
    Category category = new Category();
    category.setId(1L);
    category.setName("sample name");
    category.setSongs(new HashSet<>());
    return category;
  }

  private Category mockMap(UniversalCreateDTO dto) {
    Category category = new Category();
    category.setName(dto.getName());
    return category;
  }

  private Category mockSaved(Category pl, Long id) {
    Category category = new Category();
    category.setName(pl.getName());
    category.setId(id);
    return category;
  }

  private CategoryDTO mockMap(Category category) {
    return CategoryDTO.builder().id(category.getId()).name(category.getName())
        .create();
  }

  private String convertObjectToJsonString(CategoryDTO dto) throws JsonProcessingException {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return writer.writeValueAsString(dto);
  }
}

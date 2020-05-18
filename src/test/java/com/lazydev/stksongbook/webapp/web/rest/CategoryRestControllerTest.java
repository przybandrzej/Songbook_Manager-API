package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.service.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.CategoryMapper;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ExceptionTranslator.class).build();
  }

  @Test
  void testCreate() throws Exception {
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
    mockMvc.perform(MockMvcRequestBuilders.post(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto4)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testUpdate() throws Exception {
    CategoryDTO validDto = mockMap(getSampleCategory());
    CategoryDTO invalidDto = CategoryDTO.builder().name("test invalid").id(2L).create();
    given(service.findByIdNoException(1L)).willReturn(Optional.of(getSampleCategory()));
    given(service.findByIdNoException(2L)).willReturn(Optional.empty());

    given(mapper.map(validDto)).willReturn(getSampleCategory());
    Category validSaved = mockSaved(getSampleCategory(), 1L);
    given(service.save(getSampleCategory())).willReturn(validSaved);
    CategoryDTO dto = mockMap(validSaved);
    given(mapper.map(validSaved)).willReturn(dto);

    mockMvc.perform(MockMvcRequestBuilders.put(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(validDto)))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(validDto)));
    mockMvc.perform(MockMvcRequestBuilders.put(endpoint).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(invalidDto)))
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetById() throws Exception {
    List<Category> list = new ArrayList<>();
    given(service.findById(1L)).willAnswer(i -> {
      Long id = i.getArgument(0);
      Category category = new Category();
      category.setId(1L);
      category.setName("dummy name");
      category.setSongs(new HashSet<>());
      list.add(category);
      return category;
    });
    given(service.findById(2L)).willThrow(EntityNotFoundException.class);
    given(mapper.map(any(Category.class))).willAnswer(it -> {
      Category a = it.getArgument(0);
      return mockMap(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.get(endpoint + "/id/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(mockMap(list.get(0)))));
    mockMvc.perform(MockMvcRequestBuilders.get(endpoint + "/id/2"))
        .andExpect(status().isNotFound());
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

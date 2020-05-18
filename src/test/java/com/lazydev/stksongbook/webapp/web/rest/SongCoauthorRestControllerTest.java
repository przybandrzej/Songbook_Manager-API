package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.service.SongCoauthorService;
import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.SongCoauthorMapper;
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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({MockitoExtension.class})
class SongCoauthorRestControllerTest {

  private static final String BASE_URL = "/api/coauthors";

  @Mock
  SongCoauthorService songCoauthorService;
  @Mock
  SongCoauthorMapper mapper;
  @InjectMocks
  SongCoauthorRestController controller;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ExceptionTranslator.class).build();
  }

  @Test
  void testCreate() throws Exception {
    SongCoauthorDTO dto = SongCoauthorDTO.builder().authorId(5L).songId(1L).function("muzyka").build();
    SongCoauthorDTO dto2 = SongCoauthorDTO.builder().authorId(5L).songId(3L).function("muza").build();
    SongCoauthorDTO dto3 = SongCoauthorDTO.builder().authorId(5L).songId(4L).function("").build();
    SongCoauthorDTO dto4 = SongCoauthorDTO.builder().authorId(5L).songId(2L).function("tekst").build();
    SongCoauthor existing = map(dto4);

    given(songCoauthorService.findBySongIdAndAuthorIdNoException(1L, 5L)).willReturn(Optional.empty());
    given(songCoauthorService.findBySongIdAndAuthorIdNoException(2L, 5L)).willReturn(Optional.of(existing));
    given(songCoauthorService.save(any(SongCoauthor.class))).willAnswer(it -> {
      SongCoauthor a = it.getArgument(0);
      return a;
    });
    given(mapper.map(any(SongCoauthorDTO.class))).willAnswer(it -> {
      SongCoauthorDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(SongCoauthor.class))).willAnswer(it -> {
      SongCoauthor a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(content().json(convertObjectToJsonString(dto)));
    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto2)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto3)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto4)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testUpdate() throws Exception {
    SongCoauthorDTO dto = SongCoauthorDTO.builder().authorId(5L).songId(1L).function("muzyka").build();
    SongCoauthorDTO dto2 = SongCoauthorDTO.builder().authorId(5L).songId(3L).function("muza").build();
    SongCoauthorDTO dto3 = SongCoauthorDTO.builder().authorId(5L).songId(4L).function("").build();
    SongCoauthorDTO dto4 = SongCoauthorDTO.builder().authorId(5L).songId(2L).function("tekst").build();
    SongCoauthor existing = map(dto4);

    given(songCoauthorService.findBySongIdAndAuthorIdNoException(1L, 5L)).willReturn(Optional.empty());
    given(songCoauthorService.findBySongIdAndAuthorIdNoException(2L, 5L)).willReturn(Optional.of(existing));
    given(songCoauthorService.save(any(SongCoauthor.class))).willAnswer(it -> {
      SongCoauthor a = it.getArgument(0);
      return a;
    });
    given(mapper.map(any(SongCoauthorDTO.class))).willAnswer(it -> {
      SongCoauthorDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(SongCoauthor.class))).willAnswer(it -> {
      SongCoauthor a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto2)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto3)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto4)))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(dto4)));
  }

  private SongCoauthorDTO map(SongCoauthor songCoauthor) {
    return SongCoauthorDTO.builder().authorId(songCoauthor.getAuthor().getId())
        .songId(songCoauthor.getSong().getId()).function(songCoauthor.getFunction()).build();
  }

  private SongCoauthor map(SongCoauthorDTO dto) {
    SongCoauthor songCoauthor = new SongCoauthor();
    songCoauthor.setId(new SongsCoauthorsKey());
    Author author = new Author();
    author.setId(dto.getAuthorId());
    author.setCoauthorSongs(new HashSet<>());
    songCoauthor.setAuthor(author);
    Song song = new Song();
    song.setId(dto.getSongId());
    song.setCoauthors(new HashSet<>());
    songCoauthor.setSong(song);
    songCoauthor.setFunction(dto.getFunction());
    return songCoauthor;
  }

  private String convertObjectToJsonString(SongCoauthorDTO dto) throws JsonProcessingException {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return writer.writeValueAsString(dto);
  }
}

package com.lazydev.stksongbook.webapp.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.User;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.data.model.UsersSongsRatingsKey;
import com.lazydev.stksongbook.webapp.service.UserSongRatingService;
import com.lazydev.stksongbook.webapp.service.dto.UserSongRatingDTO;
import com.lazydev.stksongbook.webapp.service.exception.EntityNotFoundException;
import com.lazydev.stksongbook.webapp.service.mappers.UserSongRatingMapper;
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
class UserSongRatingResourceTest {

  private static final String BASE_URL = "/api/ratings";

  @Mock
  UserSongRatingService userSongRatingService;
  @Mock
  UserSongRatingMapper mapper;
  @InjectMocks
  UserSongRatingResource controller;
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ExceptionTranslator.class).build();
  }

  @Test
  void testCreate() throws Exception {
    UserSongRatingDTO dto = UserSongRatingDTO.builder().userId(5L).songId(1L).rating(BigDecimal.valueOf(-0.7)).build();
    UserSongRatingDTO dto2 = UserSongRatingDTO.builder().userId(5L).songId(3L).rating(BigDecimal.valueOf(1.5)).build();
    UserSongRatingDTO dto3 = UserSongRatingDTO.builder().userId(5L).songId(4L).rating(BigDecimal.valueOf(0.9)).build();
    UserSongRatingDTO dto4 = UserSongRatingDTO.builder().userId(5L).songId(2L).rating(BigDecimal.valueOf(0.9)).build();
    UserSongRating existing = map(dto4);

    given(userSongRatingService.findByUserIdAndSongIdNoException(5L, 4L)).willReturn(Optional.empty());
    given(userSongRatingService.findByUserIdAndSongIdNoException(5L, 2L)).willReturn(Optional.of(existing));
    given(userSongRatingService.save(any(UserSongRating.class))).willAnswer(it -> {
      UserSongRating a = it.getArgument(0);
      return a;
    });
    given(mapper.map(any(UserSongRatingDTO.class))).willAnswer(it -> {
      UserSongRatingDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(UserSongRating.class))).willAnswer(it -> {
      UserSongRating a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto2)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(dto3)))
        .andExpect(status().isCreated())
        .andExpect(content().json(convertObjectToJsonString(dto3)));
    mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto4)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testUpdate() throws Exception {
    UserSongRatingDTO dto = UserSongRatingDTO.builder().userId(5L).songId(1L).rating(BigDecimal.valueOf(-0.7)).build();
    UserSongRatingDTO dto2 = UserSongRatingDTO.builder().userId(5L).songId(3L).rating(BigDecimal.valueOf(1.5)).build();
    UserSongRatingDTO dto3 = UserSongRatingDTO.builder().userId(5L).songId(4L).rating(BigDecimal.valueOf(0.9)).build();
    UserSongRatingDTO dto4 = UserSongRatingDTO.builder().userId(5L).songId(2L).rating(BigDecimal.valueOf(0.9)).build();
    UserSongRating existing = map(dto4);

    given(userSongRatingService.findByUserIdAndSongIdNoException(5L, 4L)).willReturn(Optional.empty());
    given(userSongRatingService.findByUserIdAndSongIdNoException(5L, 2L)).willReturn(Optional.of(existing));
    given(userSongRatingService.save(any(UserSongRating.class))).willAnswer(it -> {
      UserSongRating a = it.getArgument(0);
      return a;
    });
    given(mapper.map(any(UserSongRatingDTO.class))).willAnswer(it -> {
      UserSongRatingDTO a = it.getArgument(0);
      return map(a);
    });
    given(mapper.map(any(UserSongRating.class))).willAnswer(it -> {
      UserSongRating a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL).contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(dto)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto2)))
        .andExpect(status().isBadRequest());
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto3)))
        .andExpect(status().isNotFound());
    mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
        .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(dto4)))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(dto4)));
  }

  @Test
  void testGetByUserIdAndSongId() throws Exception {
    UserSongRating rating = new UserSongRating();
    rating.setId(new UsersSongsRatingsKey());
    rating.setRating(BigDecimal.valueOf(0.9));
    User user = new User();
    user.setId(1L);
    user.setUserRatings(new HashSet<>());
    Song song = new Song();
    song.setId(1L);
    song.setRatings(new HashSet<>());
    rating.setUser(user);
    rating.setSong(song);
    given(userSongRatingService.findByUserIdAndSongId(1L, 1L)).willReturn(rating);
    given(userSongRatingService.findByUserIdAndSongId(2L, 1L)).willThrow(EntityNotFoundException.class);
    given(mapper.map(any(UserSongRating.class))).willAnswer(it -> {
      UserSongRating a = it.getArgument(0);
      return map(a);
    });

    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/1/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(convertObjectToJsonString(map(rating))));
    mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/2/1"))
        .andExpect(status().isNotFound());
  }

  private UserSongRatingDTO map(UserSongRating userSongRating) {
    return UserSongRatingDTO.builder().userId(userSongRating.getUser().getId())
        .songId(userSongRating.getSong().getId()).rating(userSongRating.getRating()).build();
  }

  private UserSongRating map(UserSongRatingDTO dto) {
    UserSongRating userSongRating = new UserSongRating();
    userSongRating.setId(new UsersSongsRatingsKey());
    User user = new User();
    user.setId(dto.getUserId());
    user.setUserRatings(new HashSet<>());
    userSongRating.setUser(user);
    Song song = new Song();
    song.setId(dto.getSongId());
    song.setRatings(new HashSet<>());
    userSongRating.setSong(song);
    userSongRating.setRating(dto.getRating());
    return userSongRating;
  }

  private String convertObjectToJsonString(UserSongRatingDTO dto) throws JsonProcessingException {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
    return writer.writeValueAsString(dto);
  }
}

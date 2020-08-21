package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.StkSongbookApplication;
import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.SongCoauthorService;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {StkSongbookApplication.class, AuthorMapperImpl.class})
class AuthorMapperTest {

  @Mock
  private SongService songService;

  @Mock
  private SongCoauthorService songCoauthorService;

  @Autowired
  private AuthorMapperImpl impl;

  private AuthorMapper mapper;

  @BeforeEach
  void setUp() {
    impl.setSongCoauthorService(songCoauthorService);
    impl.setSongService(songService);
    mapper = impl;
  }

  @Test
  void testMapToDTO() {
    Author author = new Author();
    author.setId(1L);
    author.setName("dummy name");
    author.setSongs(new HashSet<>());
    author.setCoauthorSongs(new HashSet<>());

    AuthorDTO dto = mapper.map(author);

    assertEquals(author.getName(), dto.getName());
    assertEquals(author.getId(), dto.getId());
  }

  @Test
  void testMapToEntity() {
    AuthorDTO author = AuthorDTO.builder().id(1L).name("dummy name").build();

    Song song1 = new Song();
    song1.setId(1L);
    song1.setTitle("title song1");

    given(songService.findByAuthorId(1L, null, null)).willReturn(List.of(new Song[]{song1}));
    given(songCoauthorService.findByAuthorId(1L)).willReturn(Collections.emptyList());

    Author mapped = mapper.map(author);

    assertEquals(author.getId(), mapped.getId());
    assertEquals(author.getName(), mapped.getName());
    assertEquals(1, mapped.getSongs().size());
    assertTrue(mapped.getSongs().contains(song1));
    assertEquals(0, mapped.getCoauthorSongs().size());
  }

  @Test
  void testMapFromUniversalCreateDTO() {
    UniversalCreateDTO dto = UniversalCreateDTO.builder().id(5L).name("dummy name").build();

    Author author = mapper.map(dto);

    assertEquals(dto.getName(), author.getName());
    assertNotEquals(dto.getId(), author.getId());
    assertEquals(Constants.DEFAULT_ID, author.getId());
    assertEquals(0, author.getSongs().size());
    assertEquals(0, author.getCoauthorSongs().size());
  }
}

package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.StkSongbookApplication;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.TagDTO;
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
@SpringBootTest(classes = {StkSongbookApplication.class, TagMapperImpl.class})
class TagMapperTest {

  @Mock
  private SongService songService;

  @Autowired
  private TagMapperImpl impl;

  private TagMapper mapper;

  @BeforeEach
  void setUp() {
    impl.setSongService(songService);
    mapper = impl;
  }

  @Test
  void testMapToDTO() {
    Tag tag = new Tag();
    tag.setId(1L);
    tag.setName("dummy name");
    tag.setSongs(new HashSet<>());

    TagDTO dto = mapper.map(tag);

    assertEquals(tag.getName(), dto.getName());
    assertEquals(tag.getId(), dto.getId());
  }

  @Test
  void testMapToEntity() {
    TagDTO tag = TagDTO.builder().id(1L).name("dummy name").build();

    Song song1 = new Song();
    song1.setId(1L);
    song1.setTitle("title song1");

    given(songService.findByTagId(1L, null, null)).willReturn(List.of(new Song[]{song1}));

    Tag mapped = mapper.map(tag);

    assertEquals(tag.getId(), mapped.getId());
    assertEquals(tag.getName(), mapped.getName());
    assertEquals(1, mapped.getSongs().size());
    assertTrue(mapped.getSongs().contains(song1));
  }

  @Test
  void testMapFromUniversalCreateDTO() {
    UniversalCreateDTO dto = UniversalCreateDTO.builder().id(5L).name("dummy name").build();

    Tag tag = mapper.map(dto);

    assertEquals(dto.getName(), tag.getName());
    assertNotEquals(dto.getId(), tag.getId());
    assertEquals(Constants.DEFAULT_ID, tag.getId());
    assertEquals(0, tag.getSongs().size());
  }
}

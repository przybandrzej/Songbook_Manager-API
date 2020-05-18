package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.StkSongbookApplication;
import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.CategoryDTO;
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
@SpringBootTest(classes = {StkSongbookApplication.class, CategoryMapperImpl.class})
class CategoryMapperTest {

  @Mock
  private SongService songService;

  @Autowired
  private CategoryMapperImpl impl;

  private CategoryMapper mapper;

  @BeforeEach
  void setUp() {
    impl.setSongService(songService);
    mapper = impl;
  }

  @Test
  void testMapToDTO() {
    Category category = new Category();
    category.setId(1L);
    category.setName("dummy name");
    category.setSongs(new HashSet<>());

    CategoryDTO dto = mapper.map(category);

    assertEquals(category.getName(), dto.getName());
    assertEquals(category.getId(), dto.getId());
  }

  @Test
  void testMapToEntity() {
    CategoryDTO category = CategoryDTO.builder().id(1L).name("dummy name").build();

    Song song1 = new Song();
    song1.setId(1L);
    song1.setTitle("title song1");

    given(songService.findByCategoryId(1L)).willReturn(List.of(new Song[]{song1}));

    Category mapped = mapper.map(category);

    assertEquals(category.getId(), mapped.getId());
    assertEquals(category.getName(), mapped.getName());
    assertEquals(1, mapped.getSongs().size());
    assertTrue(mapped.getSongs().contains(song1));
  }

  @Test
  void testMapFromUniversalCreateDTO() {
    UniversalCreateDTO dto = UniversalCreateDTO.builder().id(5L).name("dummy name").build();

    Category category = mapper.map(dto);

    assertEquals(dto.getName(), category.getName());
    assertNotEquals(dto.getId(), category.getId());
    assertEquals(Constants.DEFAULT_ID, category.getId());
    assertEquals(0, category.getSongs().size());
  }
}

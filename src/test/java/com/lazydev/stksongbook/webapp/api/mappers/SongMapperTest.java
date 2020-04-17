package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.SongDTO;
import com.lazydev.stksongbook.webapp.data.model.Song;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class SongMapperTest {

  @Autowired
  private SongMapper mapper;
  private SongDTO dto = SongDTO.builder().id(1L).title("test").categoryId(1L).authors(new HashSet<>()).additionTime("13-04-2020 21:57:00").curio("curio").guitarTabs("dasdsads").tagsId(List.of(1L)).lyrics("fjrkelcdfcwe").create();

  @Test
  void songDTOToSong() {
    /*Song song = mapper.songDTOToSong(dto);
    assertEquals(dto.getId(), song.getId());
    assertEquals(dto.getTitle(), song.getTitle());
    assertEquals(dto.getLyrics(), song.getLyrics());
    assertEquals(dto.getGuitarTabs(), song.getGuitarTabs());
    assertEquals(dto.getCategoryId(), song.getCategory().getId());
    assertEquals(dto.getCurio(), song.getCurio());
    //assertEquals(dto.getAdditionTime(), song.getAdditionTime().toString());
    dto.getTags().forEach(i -> assertTrue(song.getTags().stream().anyMatch(tag -> tag.getId().equals(i))));*/
  }
}
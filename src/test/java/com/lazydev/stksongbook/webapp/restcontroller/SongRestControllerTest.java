package com.lazydev.stksongbook.webapp.restcontroller;

import com.lazydev.stksongbook.webapp.dto.SongDTO;
import com.lazydev.stksongbook.webapp.model.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class SongRestControllerTest {

    @Test
    public void whenConvertSongEntityToSongDTO_thenCorrect() {
        Song song = new Song();
        song.setId(1L);
        song.setTitle("test");
        SongsAuthorsEntity author1 = Mockito.mock(SongsAuthorsEntity.class);
        song.setAuthors((Set<SongsAuthorsEntity>) author1);
        Tag tag = Mockito.mock(Tag.class);
        song.setTags((Set<Tag>) tag);
        song.setAdditionTime(LocalDateTime.now());
        Category category = new Category();
        category.setId(1L);
        category.setName("category");
        song.setCategory(category);
        song.setGuitarTabs("ABC E D");
        song.setLyrics("text test");

        SongRestController restController = Mockito.mock(SongRestController.class);

        SongDTO songDto = restController.convertToDto(song);

        assertEquals(song.getId(), songDto.getId());
        assertEquals(song.getTitle(), songDto.getTitle());
        assertEquals(song.getAuthors(), songDto.getAuthors());
        assertEquals(song.getAdditionTime().toString(), songDto.getAdditionTime());
        assertEquals(song.getTags(), songDto.getTags());
        assertEquals(song.getCategory().getId(), songDto.getCategory().getId());
        assertEquals(song.getLyrics(), songDto.getLyrics());
        assertEquals(song.getGuitarTabs(), songDto.getGuitarTabs());
    }

    /*@Test
    public void whenConvertSongDTOToSongEntity_thenCorrect() {
        SongDTO songDto = new SongDTO();
        songDto.setId(1L);
        songDto.setName("test");
        SongRestController restController = Mockito.mock(SongRestController.class);

        Song song = restController.convertToEntity(songDto);
        assertEquals(songDto.getId(), song.getId());
        assertEquals(songDto.getName(), song.getName());
    }*/
}
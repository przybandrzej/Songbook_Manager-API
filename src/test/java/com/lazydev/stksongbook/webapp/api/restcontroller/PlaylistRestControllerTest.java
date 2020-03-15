package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.PlaylistDTO;
import com.lazydev.stksongbook.webapp.data.model.Playlist;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class PlaylistRestControllerTest {

    @Test
    public void whenConvertPlaylistEntityToPlaylistDTO_thenCorrect() {
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("test");
        PlaylistRestController restController = Mockito.mock(PlaylistRestController.class);

        PlaylistDTO playlistDto = restController.convertToDto(playlist);

        assertEquals(playlist.getId(), playlistDto.getId());
        assertEquals(playlist.getName(), playlistDto.getName());
    }

    @Test
    public void whenConvertPlaylistDTOToPlaylistEntity_thenCorrect() {
        PlaylistDTO playlistDto = new PlaylistDTO();
        playlistDto.setId(1L);
        playlistDto.setName("test");
        PlaylistRestController restController = Mockito.mock(PlaylistRestController.class);

        Playlist playlist = restController.convertToEntity(playlistDto);
        assertEquals(playlistDto.getId(), playlist.getId());
        assertEquals(playlistDto.getName(), playlist.getName());
    }
}
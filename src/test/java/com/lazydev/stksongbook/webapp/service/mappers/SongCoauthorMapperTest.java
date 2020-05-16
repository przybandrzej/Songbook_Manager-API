package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.StkSongbookApplication;
import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.data.model.SongsCoauthorsKey;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {StkSongbookApplication.class, SongCoauthorMapperImpl.class})
class SongCoauthorMapperTest {

  @Mock
  private SongService songService;

  @Mock
  private AuthorService authorService;

  @Autowired
  private SongCoauthorMapperImpl impl;

  private SongCoauthorMapper mapper;

  @BeforeEach
  void setUp() {
    impl.setAuthorService(authorService);
    impl.setSongService(songService);
    mapper = impl;
  }

  @Test
  void testMapToDTO() {
    SongCoauthor songCoauthor = getSample();

    SongCoauthorDTO dto = mapper.map(songCoauthor);

    assertEquals(songCoauthor.getAuthor().getId(), dto.getAuthorId());
    assertEquals(songCoauthor.getSong().getId(), dto.getSongId());
    assertEquals(songCoauthor.getFunction(), dto.getFunction());
  }

  @Test
  void testMapToEntity() {
    SongCoauthorDTO dto = SongCoauthorDTO.builder().authorId(1L).songId(2L).function("muzyka").build();
    SongCoauthor coauthor = getSample();

    given(songService.findById(2L)).willReturn(coauthor.getSong());
    given(authorService.findById(1L)).willReturn(coauthor.getAuthor());

    SongCoauthor mapped = mapper.map(dto);

    assertEquals(dto.getAuthorId(), mapped.getAuthor().getId());
    assertEquals(dto.getSongId(), mapped.getSong().getId());
    assertEquals(dto.getFunction(), mapped.getFunction());
  }

  private SongCoauthor getSample() {
    Author author = new Author();
    author.setId(1L);
    author.setName("author name");
    author.setCoauthorSongs(new HashSet<>());
    Song song = new Song();
    song.setId(2L);
    song.setTitle("test title");
    song.setCoauthors(new HashSet<>());
    SongCoauthor songCoauthor = new SongCoauthor();
    songCoauthor.setId(new SongsCoauthorsKey());
    songCoauthor.setSong(song);
    songCoauthor.setAuthor(author);
    songCoauthor.setFunction("muzyka");
    return songCoauthor;
  }
}

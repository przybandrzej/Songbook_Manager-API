package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.SongAuthorDTO;
import com.lazydev.stksongbook.webapp.api.mappers.SongAuthorMapper;
import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import com.lazydev.stksongbook.webapp.data.model.SongsAuthorsKey;
import com.lazydev.stksongbook.webapp.data.service.AuthorService;
import com.lazydev.stksongbook.webapp.data.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class SongAuthorMapperDecorator implements SongAuthorMapper {

  @Autowired
  @Qualifier("delegate")
  private SongAuthorMapper delegate;
  @Autowired
  private AuthorService authorService;
  @Autowired
  private SongService songService;

  @Override
  public SongAuthor songsAuthorsEntityDTOToSongAuthor(SongAuthorDTO dto) {
    var songAutor = delegate.songsAuthorsEntityDTOToSongAuthor(dto);
    songAutor.setSong(songService.findById(dto.getSongId()).orElse(null));
    songAutor.setAuthor(authorService.findById(dto.getAuthorId()).orElse(null));
    songAutor.setId(new SongsAuthorsKey(dto.getSongId(), dto.getAuthorId()));
    return songAutor;
  }
}

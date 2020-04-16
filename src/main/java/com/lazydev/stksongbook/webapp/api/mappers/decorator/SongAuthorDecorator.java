package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.SongAuthorDTO;
import com.lazydev.stksongbook.webapp.api.mappers.SongAuthorMapper;
import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.SongAuthor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class SongAuthorDecorator implements SongAuthorMapper {

  @Autowired
  @Qualifier("delegate")
  private SongAuthorMapper delegate;

  @Override
  public SongAuthorDTO songsAuthorsEntityToSongAuthorDTO(SongAuthor entity) {
    var dto = delegate.songsAuthorsEntityToSongAuthorDTO(entity);
    return SongAuthorDTO.builder()
        .copy(dto)
        .authorId(entity.getAuthor().getId())
        .songId(entity.getSong().getId())
        .create();
  }

  @Override
  public SongAuthor songsAuthorsEntityDTOToSongAuthor(SongAuthorDTO dto) {
    var songAutor = delegate.songsAuthorsEntityDTOToSongAuthor(dto);
    var song = new Song();
    song.setId(dto.getSongId());
    var author = new Author();
    author.setId(dto.getAuthorId());
    songAutor.setSong(song);
    songAutor.setAuthor(author);
    return songAutor;
  }
}

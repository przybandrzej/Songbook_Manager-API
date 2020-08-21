package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.SongCoauthor;
import com.lazydev.stksongbook.webapp.data.model.SongsCoauthorsKey;
import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongCoauthorMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Setter
public abstract class SongCoauthorMapperDecorator implements SongCoauthorMapper {

  @Autowired
  @Qualifier("delegate")
  private SongCoauthorMapper delegate;
  @Autowired
  private AuthorService authorService;
  @Autowired
  private SongService songService;

  @Override
  public SongCoauthor map(SongCoauthorDTO dto) {
    var songAuthor = delegate.map(dto);
    songAuthor.setId(new SongsCoauthorsKey());
    songAuthor.setSong(songService.findById(dto.getSongId()));
    songAuthor.setAuthor(authorService.findById(dto.getAuthorId()));
    return songAuthor;
  }
}

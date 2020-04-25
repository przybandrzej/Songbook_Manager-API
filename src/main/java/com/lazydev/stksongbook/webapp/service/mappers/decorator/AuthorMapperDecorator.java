package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.Author;
import com.lazydev.stksongbook.webapp.service.SongCoauthorService;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.AuthorDTO;
import com.lazydev.stksongbook.webapp.service.mappers.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;

public abstract class AuthorMapperDecorator implements AuthorMapper {

  @Autowired
  @Qualifier("delegate")
  private AuthorMapper delegate;
  @Autowired
  private SongCoauthorService songCoauthorService;
  @Autowired
  private SongService songService;

  @Override
  public Author map(AuthorDTO dto) {
    var author = delegate.map(dto);
    author.setBiographyUrl(null);
    author.setPhotoResource(null);
    author.setCoauthorSongs(new HashSet<>(songCoauthorService.findByAuthorId(dto.getId())));
    author.setSongs(new HashSet<>(songService.findByAuthorId(dto.getId())));
    return author;
  }
}

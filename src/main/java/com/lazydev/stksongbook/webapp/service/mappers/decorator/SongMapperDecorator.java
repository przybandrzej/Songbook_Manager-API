package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.service.AuthorService;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.stream.Collectors;

public abstract class SongMapperDecorator implements SongMapper {

  @Autowired
  @Qualifier("delegate")
  private SongMapper delegate;
  @Autowired
  private TagService tagService;
  @Autowired
  private CategoryService categoryService;
  @Autowired
  private AuthorService authorService;

  @Override
  public Song map(SongDTO dto) {
    Song song = delegate.map(dto);
    song.setTags(dto.getTags().stream().map(t -> tagService.findById(t).orElse(null)).collect(Collectors.toSet()));
    song.setCategory(categoryService.findById(dto.getCategoryId()).orElse(null));
    song.setAuthor(authorService.findById(dto.getAuthorId()).orElse(null));
    return song;
  }
}

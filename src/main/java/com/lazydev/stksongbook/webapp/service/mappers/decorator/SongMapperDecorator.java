package com.lazydev.stksongbook.webapp.service.mappers.decorator;

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

  @Override
  public Song songDTOToSong(SongDTO dto) {
    Song song = delegate.songDTOToSong(dto);
    song.setTags(dto.getTags().stream().map(t -> tagService.findById(t).orElse(null)).collect(Collectors.toSet()));
    song.setCategory(categoryService.findById(dto.getCategoryId()).orElse(null));
    return song;
  }
}

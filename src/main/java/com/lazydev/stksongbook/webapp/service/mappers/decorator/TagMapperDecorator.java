package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.TagDTO;
import com.lazydev.stksongbook.webapp.service.mappers.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;

public abstract class TagMapperDecorator implements TagMapper {

  @Autowired
  @Qualifier("delegate")
  private TagMapper delegate;
  @Autowired
  private SongService songService;

  @Override
  public Tag map(TagDTO dto) {
    var tag = delegate.map(dto);
    tag.setSongs(new HashSet<>(songService.findByTagId(dto.getId())));
    return tag;
  }
}

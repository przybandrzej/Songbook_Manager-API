package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.TagDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.mappers.TagMapper;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;

@Setter
public abstract class TagMapperDecorator implements TagMapper {

  @Autowired
  @Qualifier("delegate")
  private TagMapper delegate;
  @Autowired
  private SongService songService;

  @Override
  public Tag map(TagDTO dto) {
    var tag = delegate.map(dto);
    tag.setSongs(new HashSet<>(songService.findByTagId(dto.getId(), null, null)));
    return tag;
  }

  @Override
  public Tag map(UniversalCreateDTO dto) {
    var tag = delegate.map(dto);
    tag.setId(Constants.DEFAULT_ID);
    return tag;
  }
}

package com.lazydev.stksongbook.webapp.api.mappers.decorator;

import com.lazydev.stksongbook.webapp.api.dto.SongDTO;
import com.lazydev.stksongbook.webapp.api.mappers.SongMapper;
import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

public abstract class SongMapperDecorator implements SongMapper {

  @Autowired
  @Qualifier("delegate")
  private SongMapper delegate;

  @Override
  public Song songDTOToSong(SongDTO dto) {
    Song song = delegate.songDTOToSong(dto);
    song.setTags(tagsIDsToTags(dto));
    song.setCategory(categoryIdToCategory(dto));
    return song;
  }

  public Set<Tag> tagsIDsToTags(SongDTO dto) {
    Set<Tag> tags = new HashSet<>();
    dto.getTagsId().forEach(id -> {
      var tag = new Tag();
      tag.setId(id);
      tags.add(tag);
    });
    return tags;
  }

  public Category categoryIdToCategory(SongDTO dto) {
    var category = new Category();
    category.setId(dto.getCategoryId());
    return category;
  }
}

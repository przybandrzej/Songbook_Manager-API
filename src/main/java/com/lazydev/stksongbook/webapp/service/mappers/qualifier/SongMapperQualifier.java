package com.lazydev.stksongbook.webapp.service.mappers.qualifier;

import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.service.TagService;

import java.util.ArrayList;
import java.util.List;

@SongQualifier
public class SongMapperQualifier {

  private TagService tagService;
  private CategoryService categoryService;

  public SongMapperQualifier(TagService tagService, CategoryService categoryService) {
    this.tagService = tagService;
    this.categoryService = categoryService;
  }

  @TagsIDsToTags
  public List<Tag> tagsIDsToTags(SongDTO dto) {
    List<Tag> tags = new ArrayList<>();
    dto.getTags().forEach(id -> tagService.findById(id).ifPresent(tags::add));
    return tags;
  }

  @CategoryIdToCategory
  public Category categoryIdToCategory(SongDTO dto) {
    return categoryService.findById(dto.getCategoryId()).orElse(null);
  }
}

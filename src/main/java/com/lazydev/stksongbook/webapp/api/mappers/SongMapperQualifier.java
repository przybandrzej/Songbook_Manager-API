package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.data.service.CategoryService;
import com.lazydev.stksongbook.webapp.data.service.TagService;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SongMapperQualifier {

  private TagService tagService;
  private CategoryService categoryService;

  public SongMapperQualifier(TagService tagService, CategoryService categoryService) {
    this.tagService = tagService;
    this.categoryService = categoryService;
  }

  @Named("tagsIDsToTags")
  public List<Tag> tagsIDsToTags(List<Long> list) {
    List<Tag> tags = new ArrayList<>();
    list.forEach(id -> tagService.findById(id).ifPresent(tags::add));
    return tags;
  }

  @Named("categoryIdToCategory")
  public Category categoryIdToCategory(Long id) {
    return categoryService.findById(id).orElse(null);
  }
}

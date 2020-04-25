package com.lazydev.stksongbook.webapp.service.mappers.decorator;

import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.service.SongService;
import com.lazydev.stksongbook.webapp.service.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.service.mappers.AuthorMapper;
import com.lazydev.stksongbook.webapp.service.mappers.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;

public abstract class CategoryMapperDecorator implements CategoryMapper {

  @Autowired
  @Qualifier("delegate")
  private CategoryMapper delegate;
  @Autowired
  private SongService songService;

  @Override
  public Category map(CategoryDTO dto) {
    var category = delegate.map(dto);
    category.setSongs(new HashSet<>(songService.findByCategoryId(dto.getId())));
    return category;
  }
}

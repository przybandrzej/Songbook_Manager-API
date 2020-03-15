package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.data.model.Category;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {

    CategoryDTO categoryToCategoryDTO(Category entity);
    Category categoryDTOToCategory(CategoryDTO dto);
}

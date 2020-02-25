package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Author;
import com.lazydev.stksongbook.webapp.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * There is no need to add the Mappings because the fields' names are the same
     */

    CategoryDTO categoryToCategoryDTO(Category entity);
    Category categoryDTOToCategory(CategoryDTO dto);
}

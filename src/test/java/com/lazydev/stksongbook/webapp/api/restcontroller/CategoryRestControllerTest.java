package com.lazydev.stksongbook.webapp.api.restcontroller;

import com.lazydev.stksongbook.webapp.api.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.data.model.Category;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

public class CategoryRestControllerTest {

    @Test
    public void whenConvertCategoryEntityToCategoryDTO_thenCorrect() {
        Category category = new Category();
        category.setId(1L);
        category.setName("test");
        CategoryRestController restController = Mockito.mock(CategoryRestController.class);

        CategoryDTO categoryDto = restController.convertToDto(category);

        assertEquals(category.getId(), categoryDto.getId());
        assertEquals(category.getName(), categoryDto.getName());
    }

    @Test
    public void whenConvertCategoryDTOToCategoryEntity_thenCorrect() {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setId(1L);
        categoryDto.setName("test");
        CategoryRestController restController = Mockito.mock(CategoryRestController.class);

        Category category = restController.convertToEntity(categoryDto);
        assertEquals(categoryDto.getId(), category.getId());
        assertEquals(categoryDto.getName(), category.getName());
    }
}
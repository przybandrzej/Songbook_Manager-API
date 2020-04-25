package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.service.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.CategoryMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(CategoryMapperDecorator.class)
public interface CategoryMapper {

    CategoryDTO map(Category entity);


    Category map(CategoryDTO dto);
}

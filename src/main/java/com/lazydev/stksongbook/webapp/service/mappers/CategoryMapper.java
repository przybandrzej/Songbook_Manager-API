package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.Category;
import com.lazydev.stksongbook.webapp.service.dto.CategoryDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.CategoryMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(CategoryMapperDecorator.class)
public interface CategoryMapper {

    CategoryDTO map(Category entity);

    Category map(CategoryDTO dto);

    @Mapping(target = "songs", expression = "java(new HashSet<>())")
    @Mapping(target = "id", expression = "java(Constants.DEFAULT_ID)")
    @Mapping(target = "name", source = "name")
    Category map(UniversalCreateDTO dto);
}

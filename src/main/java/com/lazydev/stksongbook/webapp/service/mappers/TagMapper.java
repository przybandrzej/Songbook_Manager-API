package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.service.dto.TagDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.UniversalCreateDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.TagMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(TagMapperDecorator.class)
public interface TagMapper {

  TagDTO map(Tag entity);

  @Mapping(target = "songs", ignore = true)
  Tag map(TagDTO dto);

  @Mapping(target = "songs", ignore = true)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "name", source = "name")
  Tag map(UniversalCreateDTO dto);
}

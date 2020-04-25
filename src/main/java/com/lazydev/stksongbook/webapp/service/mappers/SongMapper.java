package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.*;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.SongMapperDecorator;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.service.TagService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
    uses = {Tag.class, SongCoauthorMapper.class, TagService.class, CategoryService.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(SongMapperDecorator.class)
public interface SongMapper {

  @Mapping(target = "creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "categoryId", source = "entity.category.id")
  @Mapping(target = "authorId", source = "entity.author.id")
  @Mapping(target = "tags", expression = "java(convertTagsToIDs(entity.getTags()))")
  @Mapping(target = "averageRating", expression = "java(calculateAverageRating(entity.getRatings()))")
  SongDTO map(Song entity);

  @Mapping(target = "creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "tags", ignore = true)
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "author", ignore = true)
  Song map(SongDTO dto);

  default List<Long> convertTagsToIDs(Set<Tag> list) {
    return list.stream().mapToLong(Tag::getId).boxed().collect(Collectors.toList());
  }

  default Double calculateAverageRating(Set<UserSongRating> ratings) {
    if(ratings != null) {
      var optional = ratings.stream().mapToDouble(UserSongRating::getRating).average();
      if(optional.isPresent()) {
        return optional.getAsDouble();
      }
    }
    return null;
  }
}

package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.service.TagService;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.dto.creational.CreateSongDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.SongMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring",
    uses = {Tag.class, SongCoauthorMapper.class, TagService.class, CategoryService.class, CategoryMapper.class,
        AuthorMapper.class, TagMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(SongMapperDecorator.class)
public interface SongMapper {

  @Mapping(target = "creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "averageRating", expression = "java(calculateAverageRating(entity.getRatings()))")
  SongDTO map(Song entity);

  @Mapping(target = "creationTime", source = "creationTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  Song map(SongDTO dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "creationTime", ignore = true)
  @Mapping(target = "tags", ignore = true)
  @Mapping(target = "category", ignore = true)
  @Mapping(target = "author", ignore = true)
  @Mapping(target = "coauthors", ignore = true)
  Song map(CreateSongDTO dto);

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

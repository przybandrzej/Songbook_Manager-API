package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.service.CategoryService;
import com.lazydev.stksongbook.webapp.service.TagService;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import com.lazydev.stksongbook.webapp.service.mappers.decorator.SongMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring",
    uses = {Tag.class, SongCoauthorMapper.class, TagService.class, CategoryService.class, CategoryMapper.class,
        AuthorMapper.class, TagMapper.class, SongAddMapper.class, SongEditMapper.class},
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(SongMapperDecorator.class)
public interface SongMapper {

  @Mapping(target = "averageRating", expression = "java(calculateAverageRating(entity.getRatings()))")
  @Mapping(target = "isAwaiting", source = "awaiting")
  @Mapping(target = "addedBy", source = "added")
  @Mapping(target = "edits", source = "edits")
  SongDTO map(Song entity);

  @Mapping(target = "awaiting", ignore = true)
  Song map(SongDTO dto);

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

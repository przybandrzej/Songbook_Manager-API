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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

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

  default BigDecimal calculateAverageRating(Set<UserSongRating> ratings) {
    if(ratings != null && !ratings.isEmpty()) {
      Stream<BigDecimal> bigDecimals = ratings.stream().map(UserSongRating::getRating).filter(Objects::nonNull);
      BigDecimal sum = bigDecimals.reduce(BigDecimal.ZERO, BigDecimal::add);
      return sum.divide(new BigDecimal(ratings.stream().map(UserSongRating::getRating).filter(Objects::nonNull).count()), RoundingMode.HALF_UP);
    }
    return null;
  }
}

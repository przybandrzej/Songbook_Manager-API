package com.lazydev.stksongbook.webapp.service.mappers;

import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.UserSongRating;
import com.lazydev.stksongbook.webapp.service.dto.SongDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SongMapper {

  @Mapping(target = "averageRating", expression = "java(calculateAverageRating(entity.getRatings()))")
  @Mapping(target = "isAwaiting", source = "awaiting")
  @Mapping(target = "addedBy", source = "added.id")
  SongDTO map(Song entity);

  default BigDecimal calculateAverageRating(Set<UserSongRating> ratings) {
    if(ratings != null && !ratings.isEmpty()) {
      Stream<BigDecimal> bigDecimals = ratings.stream().map(UserSongRating::getRating).filter(Objects::nonNull);
      BigDecimal sum = bigDecimals.reduce(BigDecimal.ZERO, BigDecimal::add);
      return sum.divide(new BigDecimal(ratings.stream().map(UserSongRating::getRating).filter(Objects::nonNull).count()), RoundingMode.HALF_UP);
    }
    return null;
  }
}

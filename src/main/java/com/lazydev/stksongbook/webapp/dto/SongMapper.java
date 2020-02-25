package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.Song;
import com.lazydev.stksongbook.webapp.model.UsersSongsRatingsEntity;
import org.mapstruct.*;

import java.util.OptionalDouble;

@Mapper(componentModel = "spring")
public abstract class SongMapper implements ISongMapper {

    @BeforeMapping
    void calculateAverageRatings(Song song, @MappingTarget SongDTO songDto) {
        OptionalDouble average = song.getRatings().stream().mapToDouble(UsersSongsRatingsEntity::getRating).average();
        if (average.isPresent()) {
            songDto.setAverageRating(average.getAsDouble());
        } else {
            songDto.setAverageRating(0.0);
        }
    }
}

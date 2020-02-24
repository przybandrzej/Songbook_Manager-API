package com.lazydev.stksongbook.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersSongsRatingsEntityDTO {

    private SongDTO song;
    private Double userRating;

    // link to self
    // link to all user's songs ratings
    // link to user
}

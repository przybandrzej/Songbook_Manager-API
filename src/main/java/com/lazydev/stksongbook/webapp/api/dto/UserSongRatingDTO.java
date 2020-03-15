package com.lazydev.stksongbook.webapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSongRatingDTO {

    private Long songId;
    private Double userRating;

    // link to self
    // link to the real song
    // link to all user's songs ratings
    // link to user
}

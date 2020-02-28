package com.lazydev.stksongbook.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersSongsRatingsEntityDTO {

    private Long songId;
    private Double userRating;

    // link to self
    // link to the real song
    // link to all user's songs ratings
    // link to user
}

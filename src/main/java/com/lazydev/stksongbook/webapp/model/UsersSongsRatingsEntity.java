package com.lazydev.stksongbook.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "users_songs_ratings")
@Check(constraints = "rating >= 0 AND rating <= 1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersSongsRatingsEntity {


    @EmbeddedId
    private UsersSongsRatingsKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("song_id")
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(name = "rating", nullable = false)
    private Double rating;
}

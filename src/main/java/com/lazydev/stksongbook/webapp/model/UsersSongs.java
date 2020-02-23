package com.lazydev.stksongbook.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "users_songs")
@Check(constraints = "rating >= 0 AND rating <= 1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersSongs {

    @EmbeddedId
    private UsersSongsKey id;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("song_id")
    @JoinColumn(name = "song_id")
    private Song song;

    @Column(name = "rating")
    private Double rating;
}

package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "users_songs_ratings")
@Check(constraints = "rating >= 0 AND rating <= 1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSongRating {

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

  public void setUser(User user) {
    this.user = user;
    user.addRating(this);
    this.id.setUserId(user.getId());
  }

  public void setSong(Song song) {
    this.song = song;
    song.addRating(this);
    this.id.setSongId(song.getId());
  }
}

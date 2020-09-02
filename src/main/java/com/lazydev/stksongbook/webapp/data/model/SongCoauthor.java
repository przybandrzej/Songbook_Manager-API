package com.lazydev.stksongbook.webapp.data.model;

import com.lazydev.stksongbook.webapp.data.model.enumeration.CoauthorFunction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "songs_coauthors")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "song")
public class SongCoauthor {

  @EmbeddedId
  private SongsCoauthorsKey id;

  @ManyToOne
  @MapsId("song_id")
  @JoinColumn(name = "song_id")
  private Song song;

  @ManyToOne
  @MapsId("author_id")
  @JoinColumn(name = "author_id")
  private Author author;

  @Column(name = "coauthor_function", nullable = false)
  private CoauthorFunction coauthorFunction;

  public void setSong(Song song) {
    this.song = song;
    song.addCoauthor(this);
    this.id.setSongId(song.getId());
  }

  public void setAuthor(Author author) {
    this.author = author;
    author.addCoauthorSong(this);
    this.id.setAuthorId(author.getId());
  }
}

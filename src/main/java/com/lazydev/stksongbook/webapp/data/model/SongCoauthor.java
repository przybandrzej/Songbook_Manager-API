package com.lazydev.stksongbook.webapp.data.model;

import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "songs_coauthors")
@Check(constraints = "function IN (" + Constants._Function_Music_Polish + ", " + Constants._Function_Text_Polish + ")")
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

  /**
   * if null then it is the main author displayed with the title
   */
  @Column(name = "function", nullable = false)
  private String function;

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

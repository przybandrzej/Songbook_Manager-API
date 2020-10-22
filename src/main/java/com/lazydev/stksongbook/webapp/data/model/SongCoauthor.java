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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

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
}

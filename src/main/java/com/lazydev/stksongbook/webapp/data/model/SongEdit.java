package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "song_edits")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SongEdit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User editedBy;

  @ManyToOne
  @JoinColumn(name = "song_id", referencedColumnName = "id", nullable = false)
  private Song editedSong;

  @Column(name = "timestamp", nullable = false)
  private Instant timestamp = Instant.now();
}

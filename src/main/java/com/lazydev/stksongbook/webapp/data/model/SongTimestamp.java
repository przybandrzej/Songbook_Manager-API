package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "song_timestamps")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SongTimestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "song_id", referencedColumnName = "id", nullable = false)
  private Song song;

  @Column(name = "timestamp", nullable = false)
  private LocalDateTime timestamp;

}

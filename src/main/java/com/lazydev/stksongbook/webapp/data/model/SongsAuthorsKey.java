package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongsAuthorsKey implements Serializable {

  @Column(name = "song_id", nullable = false)
  private Long songId;

  @Column(name = "author_id", nullable = false)
  private Long authorId;
}

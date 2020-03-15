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
public class UsersSongsRatingsKey implements Serializable {

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "song_id")
  private Long songId;
}

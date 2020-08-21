package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the model class of the Tag entity. The table stores all tags used for tagging songs.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name = "tags")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "songs")
public class Tag {

  /**
   * @param id is the Primary Key in the table.
   * By definition, it must be unique.
   */
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * @param name stores the name of the tag. It must be unique.
   */
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToMany(mappedBy = "tags")
  private Set<Song> songs = new HashSet<>();

  public boolean addSong(Song song) {
    return this.songs.add(song);
  }

  public boolean removeSong(Song song) {
    return this.songs.remove(song);
  }
}

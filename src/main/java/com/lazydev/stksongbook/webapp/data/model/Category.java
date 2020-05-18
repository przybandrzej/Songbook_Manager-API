package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * This is the model class of the Category entity. The table stores all categories for the songs.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "songs")
public class Category {

  /**
   * @param id is the Primary Key in the table.
   * By definition, it must be unique.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * @param name stores the name of the category. It must be unique.
   */
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @OneToMany(mappedBy = "category")
  private Set<Song> songs;

  public boolean addSong(Song song) {
    return this.songs.add(song);
  }

  public boolean removeSong(Song song) {
    return this.songs.remove(song);
  }
}

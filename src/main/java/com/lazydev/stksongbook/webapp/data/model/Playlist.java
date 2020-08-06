package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the model class of the Playlist entity. It can be created by all users. Each user can create multiple instances.
 * Playlists store songs that exist in the SONGS table. By default they are user's private objects. They can be however shown
 * to other users by changing their status to public.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name = "playlists")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = "songs")
public class Playlist {

  /**
   * @param id is the Primary Key in the table.
   * By definition, it must be unique.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * @param ownerId is the Foreign Key referencing ID in the USERS table.
   * Represents the owner of the playlist.
   */
  @ManyToOne
  @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
  private User owner;

  /**
   * @param name is the name of the playlist.
   */
  @Column(name = "name", nullable = false)
  private String name;

  /**
   * @param isPrivate say whether the playlist is private or public.
   * When private, other users cannot see it and it cannot be shared.
   */
  @Column(name = "is_private", nullable = false)
  private boolean isPrivate;

  /**
   * @param creationTime stores the date and time of the playlist's creation.
   */
  @Column(name = "creation_time", nullable = false, columnDefinition = "TIMESTAMP default NOW()")
  private Instant creationTime;

  @ManyToMany
  @JoinTable(name = "playlists_songs",
      joinColumns = @JoinColumn(name = "playlist_id"),
      inverseJoinColumns = @JoinColumn(name = "song_id"))
  private Set<Song> songs = new HashSet<>();

  public void addSong(Song song) {
    songs.add(song);
    song.addPlaylist(this);
  }

  public boolean removeSong(Song song) {
    return songs.remove(song);
  }
}

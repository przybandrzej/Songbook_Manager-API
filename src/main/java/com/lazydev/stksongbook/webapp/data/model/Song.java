package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * This is the model class of the Song entity. It is the main table in the application that store all songs.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name = "songs")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"coauthors", "tags", "usersSongs", "playlists", "ratings"})
public class Song {

  /**
   * @param id is the Primary Key in the table.
   * By definition, it must be unique.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
  private Author author;

  @OneToMany(mappedBy = "song")
  private Set<SongCoauthor> coauthors;

  /**
   * @param title stores the song's title.
   */
  @Column(name = "title", nullable = false)
  private String title;

  /**
   * @param lyrics stores the lyrics of the song.
   */
  @Column(name = "lyrics", columnDefinition = "TEXT", nullable = false)
  private String lyrics;

  /**
   * Column that indicates whether the song is waiting for being accepted, edited or deleted
   */
  @Column(name = "is_awaiting", nullable = false)
  private boolean isAwaiting;

  /**
   * @param guitar_tabs stores the guitar tabs.
   */
  @Column(name = "guitar_tabs", columnDefinition = "TEXT", nullable = false)
  private String guitarTabs;

  /**
   * @param trivia is the optional bonus info about the song.
   */
  @Column(name = "trivia", columnDefinition = "TEXT")
  private String trivia;

  /**
   * @param addition_time stores the date and time of the song's insertion to the database.
   */
  @Column(name = "creation_time", nullable = false, columnDefinition = "TIMESTAMP default NOW()")
  private LocalDateTime creationTime;

  /**
   * @param categoryId is the Foreign Key referencing the ID in the CATEGORIES table.
   * It is used for determinig the category of the song.
   */
  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
  private Category category;

  @ManyToMany
  @JoinTable(name = "songs_tags",
      joinColumns = @JoinColumn(name = "song_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags;

  @ManyToMany(mappedBy = "songs")
  private Set<User> usersSongs;

  @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserSongRating> ratings;

  @ManyToMany(mappedBy = "songs")
  private Set<Playlist> playlists;

  public void setAuthor(Author author) {
    this.author = author;
  }

  public void setCategory(Category category) {
    this.category = category;
    category.addSong(this);
  }

  public void removeCategory() {
    category.removeSong(this);
    this.category = null;
  }

  public void addTag(Tag tag) {
    if(this.tags.add(tag)) {
      tag.addSong(this);
    }
  }

  public void removeTag(Tag tag) {
    this.tags.remove(tag);
    tag.removeSong(this);
  }

  public void addCoauthor(SongCoauthor coauthor) {
    this.coauthors.add(coauthor);
  }

  public boolean addRating(UserSongRating rating) {
    return this.ratings.add(rating);
  }

  public boolean addUser(User user) {
    return this.usersSongs.add(user);
  }

  public boolean addPlaylist(Playlist playlist) {
    return this.playlists.add(playlist);
  }

  public void removeCoauthor(SongCoauthor coauthor) {
    this.coauthors.remove(coauthor);
  }
}

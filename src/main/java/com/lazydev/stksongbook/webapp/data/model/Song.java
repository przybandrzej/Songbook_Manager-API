package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

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
@EqualsAndHashCode(exclude = {"coauthors", "tags", "usersSongs", "playlists", "ratings", "added", "edits", "verses"})
public class Song {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
  private Author author;

  @OneToMany(mappedBy = "song")
  private Set<SongCoauthor> coauthors = new HashSet<>();

  @Column(name = "title", nullable = false)
  private String title;

  @OneToMany(mappedBy = "song", orphanRemoval = true)
  private Set<Verse> verses = new HashSet<>();

  @Column(name = "is_awaiting", nullable = false)
  private boolean isAwaiting;

  @Column(name = "trivia", columnDefinition = "TEXT")
  private String trivia;

  @ManyToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
  private Category category;

  @ManyToMany
  @JoinTable(name = "songs_tags",
      joinColumns = @JoinColumn(name = "song_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();

  @ManyToMany(mappedBy = "songs")
  private Set<User> usersSongs = new HashSet<>();

  @OneToMany(mappedBy = "song", orphanRemoval = true)
  private Set<UserSongRating> ratings = new HashSet<>();

  @ManyToMany(mappedBy = "songs")
  private Set<Playlist> playlists = new HashSet<>();

  @OneToOne(mappedBy = "addedSong", orphanRemoval = true)
  private SongAdd added;

  @OneToMany(mappedBy = "editedSong", orphanRemoval = true)
  private Set<SongEdit> edits = new HashSet<>();

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
    coauthor.setSong(this);
  }

  public boolean addRating(UserSongRating rating) {
    if(this.ratings.add(rating)) {
      rating.setSong(this);
      return true;
    }
    return false;
  }

  public boolean removeRating(UserSongRating rating) {
    if(this.ratings.remove(rating)) {
      rating.setSong(null);
      return true;
    }
    return false;
  }

  public boolean addUser(User user) {
    return this.usersSongs.add(user);
  }

  public boolean addPlaylist(Playlist playlist) {
    return this.playlists.add(playlist);
  }

  public void removeCoauthor(SongCoauthor coauthor) {
    this.coauthors.remove(coauthor);
    coauthor.setSong(null);
  }

  public boolean addEdit(SongEdit timestamp) {
    if(this.edits.add(timestamp)) {
      timestamp.setEditedSong(this);
      return true;
    }
    return false;
  }

  public boolean removeEdit(SongEdit timestamp) {
    return this.edits.remove(timestamp);
  }

  public boolean removeEditIf(Predicate<? super SongEdit> ped) {
    return this.edits.removeIf(ped);
  }

  public void setAdded(SongAdd timestamp) {
    this.added = timestamp;
    this.added.setAddedSong(this);
  }

  public boolean removeUser(User user) {
    return this.usersSongs.remove(user);
  }

  public boolean removePlaylist(Playlist playlist) {
    return this.playlists.remove(playlist);
  }

  public boolean addVerse(Verse verse) {
    if(this.verses.add(verse)) {
      verse.setSong(this);
      return true;
    }
    return false;
  }

  public boolean removeVerse(Verse verse) {
    if(this.verses.remove(verse)) {
      verse.setSong(null);
      return true;
    }
    return false;
  }
}

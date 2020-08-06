package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the model class of the User entity. It represents real users of the application.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Check(constraints = "length(password) >= 6 AND length(username) >= 4")
@EqualsAndHashCode(exclude = {"playlists", "userRatings", "addedSongs", "editedSongs"})
public class User {

  /**
   * @param id is the Primary Key in the table. By definition, it must be unique
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * @param email must be unique. It is used for logging in to the database and the application.
   */
  @Column(name = "e_mail", nullable = false, unique = true)
  private String email;

  /**
   * @param password must be at least 5 characters. It is used for logging in to the database and the application.
   */
  @Column(name = "password", nullable = false)
  private String password;

  /**
   * @param username must be unique. It is the name displayed for other users.
   */
  @Column(name = "username", nullable = false, unique = true)
  private String username;

  /**
   * @param userRoleId is the Foreign Key from the user_roles table.
   * It is used for deteriminimg whether the user is an administrator, moderator or regular user.
   */
  @ManyToOne
  @JoinColumn(name = "user_role_id", referencedColumnName = "id", nullable = false)
  private UserRole userRole;

  /**
   * @param firstName is user's real first name. It is optional and not displayed for other users.
   */
  @Column(name = "first_name")
  private String firstName;

  /**
   * @param lastName is user's real last name. It is optional and not displayed for other users.
   */
  @Column(name = "last_name")
  private String lastName;

  @Column(name = "activated", nullable = false)
  private boolean activated = false;

  @Column(name = "registration_date", nullable = false)
  private Instant registrationDate;

  @Column(name = "image_url", length = 256)
  private String imageUrl;

  @Column(name = "activation_key", length = 20)
  private String activationKey;

  @Column(name = "reset_key", length = 20)
  private String resetKey;

  @Column(name = "reset_date")
  private Instant resetDate = null;

  @ManyToMany
  @JoinTable(name = "users_songs",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "song_id"))
  private Set<Song> songs = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserSongRating> userRatings = new HashSet<>();

  @OneToMany(mappedBy = "owner", orphanRemoval = true)
  private Set<Playlist> playlists = new HashSet<>();

  @OneToMany(mappedBy = "addedBy", orphanRemoval = true)
  private Set<SongAdd> addedSongs = new HashSet<>();

  @OneToMany(mappedBy = "editedBy", orphanRemoval = true)
  private Set<SongEdit> editedSongs = new HashSet<>();

  public User(Long id, String email, String password, String username, UserRole userRole, String firstName, String lastName,
              Set<Song> songs, Set<UserSongRating> userRatings, Set<Playlist> playlists, Set<SongAdd> addedSongs, Set<SongEdit> editedSongs) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.username = username;
    this.userRole = userRole;
    this.firstName = firstName;
    this.lastName = lastName;
    this.songs = songs;
    this.userRatings = userRatings;
    this.playlists = playlists;
    this.addedSongs = addedSongs;
    this.editedSongs = editedSongs;
  }

  public boolean removeSong(Song song) {
    return songs.remove(song);
  }

  public boolean addPlaylist(Playlist playlist) {
    if(this.playlists.add(playlist)) {
      playlist.setOwner(this);
      return true;
    }
    return false;
  }

  public boolean addRating(UserSongRating rating) {
    return this.userRatings.add(rating);
  }

  public boolean addSong(Song song) {
    if(this.songs.add(song)) {
      song.addUser(this);
      return true;
    }
    return false;
  }

  public void setUserRole(UserRole role) {
    this.userRole = role;
    role.addUser(this);
  }

  public void removeUserRole(UserRole role) {
    role.removeUser(this);
    this.userRole = null;
  }

  public boolean addAddedSong(SongAdd timestamp) {
    if(this.addedSongs.add(timestamp)) {
      timestamp.setAddedBy(this);
      return true;
    }
    return false;
  }

  public boolean removeAddedSong(SongAdd timestamp) {
    return addedSongs.remove(timestamp);
  }

  public boolean addEditedSong(SongEdit timestamp) {
    if(this.editedSongs.add(timestamp)) {
      timestamp.setEditedBy(this);
      return true;
    }
    return false;
  }

  public boolean removeEditedSong(SongEdit timestamp) {
    return this.editedSongs.remove(timestamp);
  }
}

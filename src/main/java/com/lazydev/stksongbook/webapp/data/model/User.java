package com.lazydev.stksongbook.webapp.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;
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
@Check(constraints = "length(password) >= 6 AND length(username) >= 4")
@EqualsAndHashCode(exclude = {"playlists", "userRatings"})
public @Data
class User {

  /**
   * @param id is the Primary Key in the table. By definition, it must be unique
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  /**
   * @param login must be unique. It is used for logging in to the database and the application.
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

  @ManyToMany
  @JoinTable(name = "users_songs",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "song_id"))
  private Set<Song> songs;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserSongRating> userRatings;

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Playlist> playlists;

  /**
   * @param addedSongsCount counts songs added to the database by the user. New users have it automatically set to 0
   */
    /*@Column(name = "added_songs_count", nullable = false)
    private int addedSongsCount;*/

  /**
   * @param addedSongsCount counts songs edited by the user. New users have automatically set to 0
   */
    /*@Column(name = "edited_songs_count", nullable = false)
    private int editedSongsCount;*/

  // TODO add lists of added songs and edited songs instead
}

package com.lazydev.stksongbook.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
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
@Table(name="songs")
@AllArgsConstructor
@NoArgsConstructor
@Data
//@EntityListeners(AuditingEntityListener.class)
public class Song {

    /**
     * @param id is the Primary Key in the table.
     *           By definition, it must be unique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * @param authorId is the Foreign Key referencing the ID in the AUTHORS table.
     *                By definition, it must be unique.
     */
    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SongsAuthorsEntity> authors;

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
     * @param guitar_tabs stores the guitar tabs.
     */
    @Column(name = "guitar_tabs", columnDefinition = "TEXT", nullable = false)
    private String guitarTabs;

    /**
     * @param curio is the optional bonus info about the song.
     */
    @Column(name = "curio", columnDefinition = "TEXT")
    private String curio;

    /**
     * @param addition_time stores the date and time of the song's insertion to the database.
     */
    @Column(name = "addition_time", nullable = false)
    private LocalDateTime additionTime;

    /**
     * @param categoryId is the Foreign Key referencing the ID in the CATEGORIES table.
     *                   It is used for determinig the category of the song.
     */
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "songs_tags", joinColumns = @JoinColumn(name = "song_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @ManyToMany(mappedBy = "songs")
    private Set<User> usersSongs;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UsersSongsRatingsEntity> ratings;

    @ManyToMany(mappedBy = "songs")
    private Set<Playlist> playlists;

    // TODO add icon
}

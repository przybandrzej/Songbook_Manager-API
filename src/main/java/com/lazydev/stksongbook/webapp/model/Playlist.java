package com.lazydev.stksongbook.webapp.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This is the model class of the Playlist entity. It can be created by all users. Each user can create multiple instances.
 * Playlists store songs that exist in the SONGS table. By default they are user's private objects. They can be however shown
 * to other users by changing their status to public.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name="playlists")
//@EntityListeners(AuditingEntityListener.class)
public @Data class Playlist {

    /**
     * @param id is the Primary Key in the table.
     *           By definition, it must be unique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * @param ownerId is the Foreign Key referencing ID in the USERS table.
     *                Represents the owner of the playlist.
     */
    @Column(name = "owner_id")
    @NotBlank
    @NotNull
    private long ownerId;

    /**
     * @param name is the name of the playlist.
     */
    @Column(name = "name")
    @NotBlank
    @NotNull
    private String name;

    /**
     * @param isPrivate say whether the playlist is private or public.
     *                When private, other users cannot see it and it cannot be shared.
     */
    @Column(name = "is_private")
    @NotBlank
    @NotNull
    private boolean isPrivate;

    /**
     * @param creationTime stores the date and time of the playlist's creation.
     */
    @Column(name = "creation_time")
    @NotBlank
    @NotNull
    private LocalDateTime creationTime;

    //TODO
    /*
    @Column(name = "songs")
    @NotBlank
    private ArrayList<Song> songs;*/
}

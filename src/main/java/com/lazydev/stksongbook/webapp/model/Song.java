package com.lazydev.stksongbook.webapp.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * This is the model class of the Song entity. It is the main table in the application that store all songs.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name="songs")
@EntityListeners(AuditingEntityListener.class)
public @Data class Song {

    /**
     * @param id is the Primary Key in the table.
     *           By definition, it must be unique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    /**
     * @param authorId is the Foreign Key referencing the ID in the AUTHORS table.
     *                By definition, it must be unique.
     */
    @NotBlank
    @Column(name = "author_id")
    private long authorId;

    /**
     * @param title stores the song's title.
     */
    @NotBlank
    @Column(name = "title")
    private String title;

    /**
     * @param lyrics stores the lyrics of the song.
     */
    @NotBlank
    @Column(name = "lyrics")
    private String lyrics;

    /**
     * @param guitar_tabs stores the guitar tabs.
     */
    @NotBlank
    @Column(name = "guitar_tabs")
    private String guitarTabs;

    //TODO
    @NotBlank
    @Column(name = "tags")
    private ArrayList<Tag> tags;

    /**
     * @param curio is the optional bonus info about the song.
     */
    @Column(name = "curio")
    private String curio;

    /**
     * @param addition_time stores the date and time of the song's insertion to the database.
     */
    @NotBlank
    @Column(name = "addition_time")
    private LocalDateTime additionTime;

    /**
     * @param categoryId is the Foreign Key referencing the ID in the CATEGORIES table.
     *                   It is used for determinig the category of the song.
     */
    @NotBlank
    @Column(name = "category_id")
    private long categoryId;
}

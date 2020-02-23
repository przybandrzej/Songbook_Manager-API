package com.lazydev.stksongbook.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "songs_authors")
@Check(constraints = "function IN ('Autor', 'Muzyka', 'Tekst')")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongsAuthors {

    @EmbeddedId
    private SongsAuthorsKey id;

    @ManyToOne
    @MapsId("song_id")
    @JoinColumn(name = "song_id")
    private Song song;

    @ManyToOne
    @MapsId("author_id")
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(name = "function", nullable = false)
    private String function;
}

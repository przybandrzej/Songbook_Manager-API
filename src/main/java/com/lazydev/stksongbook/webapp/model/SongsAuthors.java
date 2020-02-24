package com.lazydev.stksongbook.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import javax.persistence.*;

@Entity
@Table(name = "songs_authors")
//@Check(constraints = "function IN ('" + SongsAuthors._Function_Author_Polish + "', '" + SongsAuthors._Function_Music_Polish + "', '" + SongsAuthors._Function_Text_Polish + "')")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SongsAuthors {

    /*public final static String _Function_Author_Polish = "Autor";
    public final static String _Function_Music_Polish = "Muzyka";
    public final static String _Function_Text_Polish = "Tekst";*/

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
    @Enumerated(EnumType.STRING)
    private SongsAuthorsFunctionsEnum function;
}

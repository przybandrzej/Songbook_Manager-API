package com.lazydev.stksongbook.webapp.model;

import lombok.*;
import org.springframework.data.annotation.Reference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * This is the model class of the Author entity. The table stores all authors of the songs from the SONGS table.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name="authors")
//@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public @Data class Author {

    /**
     * @param id is the Primary Key in the table.
     *           By definition, it must be unique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * @param name stores the name of the author. It can be a real person or a band name.
     *             It must be unique.
     */
    @NotNull
    @NotBlank
    @Column(name = "name")
    private String name;
}

package com.lazydev.stksongbook.webapp.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * This is the model class of the Tag entity. The table stores all tags used for tagging songs.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name="tags")
@EntityListeners(AuditingEntityListener.class)
public @Data class Tag {

    /**
     * @param id is the Primary Key in the table.
     *           By definition, it must be unique.
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * @param name stores the name of the tag. It must be unique.
     */
    @Column(name = "name")
    @NotBlank
    private String tagName;

    /**
     * @param tagPrefix says what character to use to display tags in the application.
     */
    private static char tagPrefix = '#';
}

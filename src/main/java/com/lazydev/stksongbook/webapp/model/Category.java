package com.lazydev.stksongbook.webapp.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * This is the model class of the Category entity. The table stores all categories for the songs.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name="categories")
@EntityListeners(AuditingEntityListener.class)
public @Data class Category {

    /**
     * @param id is the Primary Key in the table.
     *           By definition, it must be unique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    /**
     * @param name stores the name of the category. It must be unique.
     */
    @NotBlank
    @Column(name = "name")
    private String name;
}

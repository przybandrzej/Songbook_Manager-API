package com.lazydev.stksongbook.webapp.model;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Authors")
@EntityListeners(AuditingEntityListener.class)
public @Data class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @NotBlank
    @Column(name = "name")
    private String name;
}

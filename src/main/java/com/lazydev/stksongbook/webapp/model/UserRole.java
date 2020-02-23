package com.lazydev.stksongbook.webapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * This is the model class of the User Role entity. It is used for determining whether the User is an admin,
 * moderator or regular user and giving them proper permissions within the application.
 *
 * @author Andrzej Przybysz
 * @version 1.0
 */

@Entity
@Table(name="user_roles")
@AllArgsConstructor
@NoArgsConstructor
//@EntityListeners(AuditingEntityListener.class)
public @Data class UserRole {

    /**
     * @param id is the Primary Key in the table. By definition, it must be unique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    /**
     * @param name represents the name of the role. It must be unique.
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    //TODO add some kind of permissions
}

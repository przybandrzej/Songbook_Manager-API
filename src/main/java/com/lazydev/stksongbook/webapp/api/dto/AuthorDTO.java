package com.lazydev.stksongbook.webapp.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AuthorDTO {

    // link to self
    // link to all authors

    // link to songs of this author

    private final Long id;
    private final String name;

    private AuthorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;

        public AuthorDTO create() {
            return new AuthorDTO(id, name);
        }
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}

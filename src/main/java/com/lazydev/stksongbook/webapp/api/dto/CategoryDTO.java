package com.lazydev.stksongbook.webapp.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CategoryDTO {

    // link to self
    //link to all categories
    // link to songs with this category

    private final Long id;
    private final String name;

    private CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;

        public CategoryDTO create() {
            return new CategoryDTO(id, name);
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

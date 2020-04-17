package com.lazydev.stksongbook.webapp.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class TagDTO {

    private final Long id;
    private final String name;

    private TagDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TagDTO.Builder builder() {
        return new TagDTO.Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;

        public TagDTO create() {
            return new TagDTO(id, name);
        }
        public TagDTO.Builder id(Long id) {
            this.id = id;
            return this;
        }
        public TagDTO.Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}

package com.lazydev.stksongbook.webapp.service.dto;

import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@EqualsAndHashCode
public class TagDTO {

    @NotNull(message = "ID must be defined.")
    private final Long id;

    @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
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

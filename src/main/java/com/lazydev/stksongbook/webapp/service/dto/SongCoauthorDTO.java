package com.lazydev.stksongbook.webapp.service.dto;

import com.lazydev.stksongbook.webapp.service.validators.CoauthorFunctionConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@EqualsAndHashCode
public class SongCoauthorDTO {

    @NotNull(message = "Song ID must be defined.")
    private final Long songId;

    @NotNull(message = "Author ID must be defined.")
    private final Long authorId;

    @CoauthorFunctionConstraint
    private final String function;

    private SongCoauthorDTO(Long songId, Long authorId, String function) {
        this.songId = songId;
        this.authorId = authorId;
        this.function = function;
    }

    public static SongCoauthorDTO.Builder builder() {
        return new SongCoauthorDTO.Builder();
    }

    public static final class Builder {
        private Long songId;
        private Long authorId;
        private String function;

        public SongCoauthorDTO create() {
            return new SongCoauthorDTO(songId, authorId, function);
        }
        public SongCoauthorDTO.Builder songId(Long id) {
            this.songId = id;
            return this;
        }
        public SongCoauthorDTO.Builder authorId(Long id) {
            this.authorId = id;
            return this;
        }
        public SongCoauthorDTO.Builder function(String function) {
            this.function = function;
            return this;
        }
    }
}

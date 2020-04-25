package com.lazydev.stksongbook.webapp.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class SongCoauthorDTO {

    private final Long songId;
    private final Long authorId;
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

package com.lazydev.stksongbook.webapp.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@EqualsAndHashCode
public class PlaylistDTO {

    private final Long id;
    private final String name;
    private final Long ownerId;
    private final boolean isPrivate;
    private final String creationTime;
    private final Set<Long> songs;

    private PlaylistDTO(Long id, String name, Long ownerId, boolean isPrivate, String creationTime, Set<Long> songs) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.isPrivate = isPrivate;
        this.creationTime = creationTime;
        this.songs = songs;
    }

    public static PlaylistDTO.Builder builder() {
        return new PlaylistDTO.Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Long ownerId;
        private boolean isPrivate;
        private String creationTime;
        private Set<Long> songs;

        public PlaylistDTO create() {
            return new PlaylistDTO(id, name, ownerId, isPrivate, creationTime, songs);
        }
        public PlaylistDTO.Builder id(Long id) {
            this.id = id;
            return this;
        }
        public PlaylistDTO.Builder name(String name) {
            this.name = name;
            return this;
        }
        public PlaylistDTO.Builder ownerId(Long id) {
            this.ownerId = id;
            return this;
        }
        public PlaylistDTO.Builder isPrivate(boolean isPrivatee) {
            this.isPrivate = isPrivatee;
            return this;
        }
        public PlaylistDTO.Builder creationTime(String creationTime) {
            this.creationTime = creationTime;
            return this;
        }
        public PlaylistDTO.Builder songs(Set<Long> songs) {
            this.songs = songs;
            return this;
        }
    }
}
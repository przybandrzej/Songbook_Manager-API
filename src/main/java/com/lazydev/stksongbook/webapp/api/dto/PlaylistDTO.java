package com.lazydev.stksongbook.webapp.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class PlaylistDTO {

    private final Long id;
    private final String name;
    private final Long ownerId;
    private final boolean isPrivate;
    private final String creationTime;
    private final List<Long> songs;

    private PlaylistDTO(Long id, String name, Long ownerId, boolean isPrivate, String creationTime, List<Long> songs) {
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
        private List<Long> songs;

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
        public PlaylistDTO.Builder songs(List<Long> songs) {
            this.songs = songs;
            return this;
        }
        public PlaylistDTO.Builder copy(PlaylistDTO source) {
            this.id = source.id;
            this.name = source.name;
            this.ownerId = source.ownerId;
            this.isPrivate = source.isPrivate;
            this.creationTime = source.creationTime;
            this.songs = List.copyOf(source.songs);
            return this;
        }
    }
}
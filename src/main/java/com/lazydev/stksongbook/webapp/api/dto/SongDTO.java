package com.lazydev.stksongbook.webapp.api.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class SongDTO {

    private final Long id;
    private final Long categoryId;
    private final String title;
    private final List<SongAuthorDTO> authors;
    private final String lyrics;
    private final String guitarTabs;
    private final String curio;
    private final String additionTime;
    private final List<Long> tagsId;

    // link to self
    // link to all songs
    // link to all users' libraries that have this song in the library
    // link to all public playlists that include this song

    private SongDTO(Long id, Long categoryId, String title, List<SongAuthorDTO> authors, String lyrics,
                    String guitarTabs, String curio, String additionTime, List<Long> tagsId) {
        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.authors = authors;
        this.lyrics = lyrics;
        this.guitarTabs = guitarTabs;
        this.curio = curio;
        this.additionTime = additionTime;
        this.tagsId = tagsId;
    }

    public static SongDTO.Builder builder() {
        return new SongDTO.Builder();
    }

    public static final class Builder {
        private Long id;
        private Long categoryId;
        private String title;
        private List<SongAuthorDTO> authors;
        private String lyrics;
        private String guitarTabs;
        private String curio;
        private String additionTime;
        private List<Long> tagsId;

        public SongDTO create() {
            return new SongDTO(id, categoryId, title, authors, lyrics, guitarTabs, curio, additionTime, tagsId);
        }
        public SongDTO.Builder id(Long id) {
            this.id = id;
            return this;
        }
        public SongDTO.Builder categoryId(Long id) {
            this.categoryId = id;
            return this;
        }
        public SongDTO.Builder title(String title) {
            this.title = title;
            return this;
        }
        public SongDTO.Builder authors(List<SongAuthorDTO> authors) {
            this.authors = authors;
            return this;
        }
        public SongDTO.Builder lyrics(String lyrics) {
            this.lyrics = lyrics;
            return this;
        }
        public SongDTO.Builder guitarTabs(String guitarTabs) {
            this.guitarTabs = guitarTabs;
            return this;
        }
        public SongDTO.Builder curio(String curio) {
            this.curio = curio;
            return this;
        }
        public SongDTO.Builder additionTime(String additionTime) {
            this.additionTime = additionTime;
            return this;
        }
        public SongDTO.Builder tagsId(List<Long> tagsId) {
            this.tagsId = tagsId;
            return this;
        }
    }
}

package com.lazydev.stksongbook.webapp.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@EqualsAndHashCode
public class SongDTO {

    private final Long id;
    private final Long authorId;
    private final Long categoryId;
    private final String title;
    private final Set<SongCoauthorDTO> coauthors;
    private final String lyrics;
    private final String guitarTabs;
    private final String curio;
    private final String creationTime;
    private final Double averageRating;
    private final List<Long> tags;

    private SongDTO(Long id, Long authorId, Long categoryId, String title, Set<SongCoauthorDTO> coauthors, String lyrics,
                    String guitarTabs, String curio, String creationTime, List<Long> tagsId, Double averageRating) {
        this.id = id;
        this.authorId = authorId;
        this.categoryId = categoryId;
        this.title = title;
        this.coauthors = coauthors;
        this.lyrics = lyrics;
        this.guitarTabs = guitarTabs;
        this.curio = curio;
        this.creationTime = creationTime;
        this.tags = tagsId;
        this.averageRating = averageRating;
    }

    public static SongDTO.Builder builder() {
        return new SongDTO.Builder();
    }

    public static final class Builder {
        private Long id;
        private Long authorId;
        private Long categoryId;
        private String title;
        private Set<SongCoauthorDTO> coauthors;
        private String lyrics;
        private String guitarTabs;
        private String curio;
        private String creationTime;
        private List<Long> tags;
        private Double averageRating;

        public SongDTO create() {
            return new SongDTO(id, authorId, categoryId,
                title, coauthors, lyrics, guitarTabs, curio, creationTime, tags, averageRating);
        }
        public SongDTO.Builder id(Long id) {
            this.id = id;
            return this;
        }
        public SongDTO.Builder authorId(Long id) {
            this.authorId = id;
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
        public SongDTO.Builder coauthors(Set<SongCoauthorDTO> coauthors) {
            this.coauthors = coauthors;
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
        public SongDTO.Builder creationTime(String creationTime) {
            this.creationTime = creationTime;
            return this;
        }
        public SongDTO.Builder tags(List<Long> tags) {
            this.tags = tags;
            return this;
        }
        public SongDTO.Builder averageRating(Double averageRating) {
            this.averageRating = averageRating;
            return this;
        }
    }
}

package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.lazydev.stksongbook.webapp.service.dto.SongCoauthorDTO;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
@Builder
public class CreateSongDTO {

    Long authorId;
    Long categoryId;
    String title;
    Set<SongCoauthorDTO> coauthors;
    String lyrics;
    String guitarTabs;
    String curio;
    List<Long> tags;
}

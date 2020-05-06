package com.lazydev.stksongbook.webapp.service.dto.creational;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
@Builder
public class CreateSongDTO {

    String authorName;
    Long categoryId;
    String title;
    Set<CreateCoauthorDTO> coauthors;
    String lyrics;
    String guitarTabs;
    String curio;
    List<String> tags;
}

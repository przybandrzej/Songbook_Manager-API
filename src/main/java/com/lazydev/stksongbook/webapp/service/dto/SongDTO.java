package com.lazydev.stksongbook.webapp.service.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
@Builder
public class SongDTO {

  Long id;
  AuthorDTO author;
  CategoryDTO category;
  String title;
  Set<SongCoauthorDTO> coauthors;
  String lyrics;
  String guitarTabs;
  String curio;
  String creationTime;
  Double averageRating;
  List<TagDTO> tags;
}

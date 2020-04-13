package com.lazydev.stksongbook.webapp.api.mappers;

import com.lazydev.stksongbook.webapp.api.dto.SongDTO;
import com.lazydev.stksongbook.webapp.data.model.Song;
import com.lazydev.stksongbook.webapp.data.model.Tag;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {Tag.class, SongMapperQualifier.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SongMapper {

  @Mapping(target = "additionTime", source = "additionTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "categoryId", source = "entity.category.id")
  @Mapping(target = "tagsId", expression = "java(convertTagsToIDs(entity.getTags()))")
  SongDTO songToSongDTO(Song entity);

  @Mapping(target = "additionTime", source = "additionTime", dateFormat = "dd-MM-yyyy HH:mm:ss")
  @Mapping(target = "category", qualifiedByName = "categoryIdToCategory")
  @Mapping(target = "tags", qualifiedByName = "tagsIDsToTags")
  Song songDTOToSong(SongDTO dto);

  default List<Long> convertTagsToIDs(Set<Tag> list) {
    return list.stream().mapToLong(Tag::getId).boxed().collect(Collectors.toList());
  }
}

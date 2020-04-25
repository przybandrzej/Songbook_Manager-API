package com.lazydev.stksongbook.webapp.service.dto.creational;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class CreatePlaylistDTO {

    String name;
    Long ownerId;
    boolean isPrivate;
    Set<Long> songs;
}
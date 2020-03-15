package com.lazydev.stksongbook.webapp.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {

    private Long id;
    private String name;

    // link to self
    // link to all tags

    /// link to all songs with this tag
}

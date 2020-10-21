package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.util.Constants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ApiModel(description = "Class representing a DTO for creating songs in the application.")
@Value
@JsonDeserialize(builder = CreateSongDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class CreateSongDTO {

  @ApiModelProperty(notes = "Author's name (doesn't have to exist)", example = "John the band", required = true, position = 1)
  @NotBlank(message = "Field can't be blank.")
  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
  String authorName;

  @ApiModelProperty(notes = "Category", required = true, position = 2)
  @NotNull(message = "Category ID must be set.")
  Long categoryId;

  @ApiModelProperty(example = "Rolling in the deep", required = true, position = 3)
  @NotBlank(message = "Field can't be blank.")
  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.TITLE_INVALID_MESSAGE)
  String title;

  @ApiModelProperty(position = 4)
  @NotNull(message = "Coauthors list must be initialized.")
  List<@Valid CreateCoauthorDTO> coauthors = new ArrayList<>();

  @ApiModelProperty(position = 5)
  @NotNull(message = "Verses list must be initialized.")
  List<@Valid CreateVerseDTO> verses = new ArrayList<>();

  @ApiModelProperty(example = "Has written it for his wife.", position = 6)
  String trivia;

  @ApiModelProperty(required = true, position = 7)
  @NotNull(message = "Tags list must be initialized.")
  List<
      @NotBlank(message = "Field can't be blank.")
      @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
          String> tags;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}

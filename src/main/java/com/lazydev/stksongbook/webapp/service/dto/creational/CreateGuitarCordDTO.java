package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@ApiModel(description = "Class representing a DTO for creating guitar cords for lines of the songs.")
@Value
@JsonDeserialize(builder = CreateGuitarCordDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class CreateGuitarCordDTO {

  @ApiModelProperty(notes = "Content of the cord in the song", position = 1)
  @NotNull(message = "Content cannot be null")
  String content;

  @ApiModelProperty(notes = "Position in the line of the cord.", position = 2)
  @NotNull(message = "Cord cannot be null")
  Integer position;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}

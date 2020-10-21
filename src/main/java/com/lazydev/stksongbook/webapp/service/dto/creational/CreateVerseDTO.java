package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "Class representing a DTO for creating verses of songs.")
@Value
@JsonDeserialize(builder = CreateVerseDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class CreateVerseDTO {

  @ApiModelProperty(notes = "Is the verse a chorus", position = 1)
  boolean isChorus = false;

  @ApiModelProperty(notes = "Order of the verse in the song", position = 2)
  @NotNull(message = "Order cannot be null")
  Integer order;

  @ApiModelProperty(position = 3)
  @NotNull(message = "Lines list must be initialized.")
  List<@Valid CreateLineDTO> lines = new ArrayList<>();

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}

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

@ApiModel(description = "Class representing a DTO for creating lines of verses.")
@Value
@JsonDeserialize(builder = CreateLineDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class CreateLineDTO {

  @ApiModelProperty(notes = "Is the verse a chorus", position = 1)
  boolean isChorus = false;

  @ApiModelProperty(notes = "Order of the line in the song", position = 2)
  @NotNull(message = "Order must be defined.")
  Long order;

  @ApiModelProperty(notes = "Content of the line in the song", position = 3)
  @NotNull(message = "Content cannot be null")
  String content;

  @ApiModelProperty(position = 4)
  @NotNull(message = "Guitar cords list must be initialized.")
  List<@Valid CreateGuitarCordDTO> cords = new ArrayList<>();

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {
  }
}

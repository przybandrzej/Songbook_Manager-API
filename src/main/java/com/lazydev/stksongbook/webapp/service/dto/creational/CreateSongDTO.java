package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSongDTO {

  @NotBlank(message = "Field can't be blank.")
  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
  String authorName;

  @NotNull(message = "Category ID must be set.")
  Long categoryId;

  @NotBlank(message = "Field can't be blank.")
  @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.TITLE_INVALID_MESSAGE)
  String title;

  @Valid
  Set<CreateCoauthorDTO> coauthors;

  @NotBlank(message = "Lyrics can't be blank.")
  String lyrics;

  @NotBlank(message = "Guitar tabs can't be blank.")
  String guitarTabs;

  String curio;

  @NotNull(message = "Tags list must be initialized.")
  List<
      @NotBlank(message = "Field can't be blank.")
      @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
          String> tags;
}

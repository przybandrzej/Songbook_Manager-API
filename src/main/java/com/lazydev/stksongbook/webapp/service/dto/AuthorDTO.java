package com.lazydev.stksongbook.webapp.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.net.URL;

@Value
@JsonDeserialize(builder = AuthorDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class AuthorDTO {

    @NotNull(message = "ID must be defined.")
    Long id;

    @NotNull(message = "Can't be null.")
    @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
    String name;

    URL biographyUrl;

    String photoResource;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

    }
}

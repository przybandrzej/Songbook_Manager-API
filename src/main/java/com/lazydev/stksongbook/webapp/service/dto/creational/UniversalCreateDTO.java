package com.lazydev.stksongbook.webapp.service.dto.creational;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Value
@JsonDeserialize(builder = UniversalCreateDTO.Builder.class)
@Builder(builderClassName = "Builder", toBuilder = true)
public class UniversalCreateDTO {

    Long id;

    @NotNull(message = "Can't be null.")
    @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
    String name;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
    }
}

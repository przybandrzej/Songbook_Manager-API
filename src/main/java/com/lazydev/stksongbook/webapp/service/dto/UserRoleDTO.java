package com.lazydev.stksongbook.webapp.service.dto;

import com.lazydev.stksongbook.webapp.util.Constants;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@EqualsAndHashCode
public class UserRoleDTO {

    @NotNull(message = "ID must be defined.")
    private final Long id;

    @NotNull(message = "Can't be null.")
    @Pattern(regexp = Constants.NAME_REGEX_SHORT, message = Constants.NAME_SHORT_INVALID_MESSAGE)
    private final String name;

    private UserRoleDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static UserRoleDTO.Builder builder() {
        return new UserRoleDTO.Builder();
    }

    public static final class Builder {
        private Long id;
        private String name;

        public UserRoleDTO create() {
            return new UserRoleDTO(id, name);
        }
        public UserRoleDTO.Builder id(Long id) {
            this.id = id;
            return this;
        }
        public UserRoleDTO.Builder name(String name) {
            this.name = name;
            return this;
        }
    }
}

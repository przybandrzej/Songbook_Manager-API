package com.lazydev.stksongbook.webapp.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class UserRoleDTO {

    private final Long id;
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

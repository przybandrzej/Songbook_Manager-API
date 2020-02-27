package com.lazydev.stksongbook.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO {

    private Long id;
    private String name;

    // link to self
    // link to all userRoles
    // link to all users with this role
}

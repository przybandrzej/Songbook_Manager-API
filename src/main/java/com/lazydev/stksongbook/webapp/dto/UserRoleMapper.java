package com.lazydev.stksongbook.webapp.dto;

import com.lazydev.stksongbook.webapp.model.UserRole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    /**
     * There is no need to add the Mappings because the fields' names are the same
     */

    UserRoleDTO userRoleToUserRoleDTO(UserRole entity);
    UserRole userRoleDTOToUserRole(UserRoleDTO dto);
}
